/*
 * ex2.c
 *name: Avi Kaufman ID: 205462591
 */
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <curl/curl.h>
#include <string.h>
#include <signal.h>

#include <semaphore.h>

#define HTTP_OK 200L
#define REQUEST_TIMEOUT_SECONDS 2L
#define SNAME "/mysem"
#define URL_OK 0
#define URL_UNKNOWN -1
#define URL_ERROR -2

#define MAX_PROCESSES 1024

const char URL_PREFIX[] = "http";

volatile typedef struct ResultStruct{
    double sum;
    int amount, unknown;
} ResultStruct ;


void usage() {
    fprintf(stderr, "usage:\n\t./ex2 num_of_processes FILENAME\n");
    exit(EXIT_FAILURE);
}

double check_url(const char *url) {
    CURL *curl;
    CURLcode res;
    double response_time = URL_UNKNOWN;

    curl = curl_easy_init();

    if(strncmp(url, URL_PREFIX, strlen(URL_PREFIX)) != 0){
        return URL_ERROR;
    }

    if(curl) {
        curl_easy_setopt(curl, CURLOPT_URL, url);
        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);
        curl_easy_setopt(curl, CURLOPT_TIMEOUT, REQUEST_TIMEOUT_SECONDS);
        curl_easy_setopt(curl, CURLOPT_NOBODY, 1L); /* do a HEAD request */

        res = curl_easy_perform(curl);
        if(res == CURLE_OK) {
            curl_easy_getinfo(curl, CURLINFO_NAMELOOKUP_TIME, &response_time);
        }

        curl_easy_cleanup(curl);

    }

    return response_time;

}

void serial_checker(const char *filename) {

    ResultStruct results = {0};

    FILE *toplist_file;
    char *line = NULL;
    size_t len = 0;
    ssize_t read;
    double res;
    int rc, cd;

    ResultStruct *mmappedData;
    if((mmappedData = mmap(NULL, sizeof(ResultStruct), PROT_READ|PROT_WRITE, MAP_SHARED|MAP_ANONYMOUS, -1, 0)) == MAP_FAILED)
    {
        perror("unable to create mapping");
        exit(EXIT_FAILURE);
    }
    mmappedData->sum = 0;
    mmappedData->amount = 0;
    mmappedData->unknown = 0;


    toplist_file = fopen(filename, "r");

    if (toplist_file == NULL) {
        exit(EXIT_FAILURE);
    }

    while ((read = getline(&line, &len, toplist_file)) != -1) {
        if (read == -1) {
            perror("unable to read line from file");
        }
        line[read-1] = '\0'; /* null-terminate the URL */
        if (URL_UNKNOWN == (res = check_url(line))) {
            mmappedData->unknown = mmappedData->unknown + 1;
        }
        else if(res == URL_ERROR){
            printf("Illegal url detected, exiting now\n");
            exit(0);
        }
        else {
            mmappedData->sum = mmappedData->sum + res;
            mmappedData->amount = mmappedData->amount + 1;
        }
    }

    free(line);
    fclose(toplist_file);



    if(mmappedData->amount > 0){
        printf("%.4f Average response time from %d sites, %d Unknown\n",
               mmappedData->sum / mmappedData->amount,
               mmappedData->amount,
               mmappedData->unknown);
    }
    else{
        printf("No Average response time from 0 sites, %d Unknown\n", results.unknown);
    }
    if((rc = munmap(mmappedData, sizeof(ResultStruct))) != 0)
    {
        perror("unable to unmapping");
        exit(EXIT_FAILURE);
    }
}

/**
 * @define - handle single worker that run on child process
 */
void worker_mmap_checker(int worker_id, int num_of_workers, const char *filename, ResultStruct *mmappedData) {
    ResultStruct results = {0};

    double res;
    FILE *toplist_file;
    char *line = NULL;
    size_t length;
    length = 0;
    ssize_t read;
    int line_number = 0, rc, cd;

    // open file for read only
    toplist_file = fopen(filename, "r");

    // validate file open successfully
    if (toplist_file == NULL) {
        exit(EXIT_FAILURE);
    }

    //use the named semaphore
    sem_t *sem = sem_open(SNAME, 0);

    // go over all the lines
    while ((read = getline(&line, &length, toplist_file)) != -1) {
        if (read < 0) {
            perror("unable to read line from file");
        }

        if ((line_number % num_of_workers) == worker_id) {
            line[read-1] = '\0';
            if (URL_UNKNOWN == (res = check_url(line))) {
                results.unknown++;
            }
            else if (res == URL_ERROR) {
                printf("Illegal url detected, exiting now\n");
                free(line);
                fclose(toplist_file);
                kill(0,SIGKILL);
                exit(EXIT_FAILURE);
            }
            else {
                results.sum = results.sum + res;
                results.amount++;
            }
        }
        line_number++;
    }
    sem_wait(sem); //use semaphore to write in the critical section

    //write the result to mapping
    mmappedData->sum = mmappedData->sum + results.sum;
    mmappedData->amount = mmappedData->amount + results.amount;
    mmappedData->unknown = mmappedData->unknown + results.unknown;

    msync(mmappedData, sizeof(ResultStruct), MS_SYNC);
    sem_post(sem);

    // close the resources
    free(line);
    fclose(toplist_file);
}

/**
 * Handle separate the work between process and merge the results
 */
void parallel_mmap_checker(int num_of_processes, const char *filename) {

    ResultStruct res = {0};
    int work_id, fd, cd, rc;
    ResultStruct *mapData;

    if((mapData = mmap(NULL, sizeof(ResultStruct), PROT_READ|PROT_WRITE, MAP_SHARED|MAP_ANONYMOUS, -1, 0)) == MAP_FAILED) {
        perror("unable to create mapping");
        exit(EXIT_FAILURE);
    }
    mapData->sum = 0;
    mapData->amount = 0;
    mapData->unknown = 0;
    msync(mapData, sizeof(ResultStruct), MS_SYNC);

    //initailize semaphore for the childern to write in the same file
    sem_t *sem = sem_open(SNAME, O_CREAT, 0644, 1);

    pid_t p;
    // Start num_of_processes new workers
    for (work_id = 0; work_id  < num_of_processes; ++work_id ) {

        // TODO - fork the children and call worker_mmap_checker.
        // Possible implementation: Let worker_mmap_checker on which rows to perform work (from file).
        // each worker is working in different process
        p = fork();
        if (p == -1) {
            perror("unable to mapping");
            exit(EXIT_FAILURE);
        }
        else if (p == 0) {
            worker_mmap_checker(work_id, num_of_processes, filename, mapData);
            exit(EXIT_SUCCESS);
        }
    }
    // pending all child process
    while(wait(NULL) > 0);

    // get results
    res.amount = mapData->amount;
    res.sum = mapData->sum;
    res.unknown = mapData->unknown;

    // print the total results
    if(res.amount > 0) {
        printf("%.4f Average response time from %d sites, %d Unknown\n",
               res.sum / res.amount,
               res.amount,
               res.unknown);
    }
    else {
        printf("No Average response time from 0 sites, %d Unknown\n", res.unknown);
    }
    if((rc = munmap(mapData, sizeof(ResultStruct))) != 0) {
        perror("unable to unmapping");
        exit(EXIT_FAILURE);
    }
}

void worker_pipe_checker(int worker_id, int num_of_workers, const char *filename, int pipe_write_fd) {
    ResultStruct results = {0};

    double res;
    FILE *toplist_file;
    char *line = NULL;
    size_t len = 0;
    ssize_t read;
    int line_number = 0;

    toplist_file = fopen(filename, "r");

    if (toplist_file == NULL) {
        exit(EXIT_FAILURE);
    }

    // go over all the lines
    while ((read = getline(&line, &len, toplist_file)) != -1) {
        if (read == -1) {
            perror("unable to read line from file");
        }

        if ((line_number % num_of_workers) == worker_id) {
            line[read-1] = '\0';
            if(URL_UNKNOWN == (res = check_url(line))) {
                results.unknown++;
            }
            else if(res == URL_ERROR) {
                printf("Illegal url detected, exiting now\n");
                free(line);
                fclose(toplist_file);
                kill(0,SIGKILL);
                exit(EXIT_FAILURE);
            }
            else {
                results.sum = results.sum + res;
                results.amount++;
            }
        }
        line_number++;
    }

    free(line);
    fclose(toplist_file);

    int w = write(pipe_write_fd, &results, sizeof(results));
    if(w == -1) {
        perror("unable to write results to pipe");
        exit(EXIT_FAILURE);
    }
    int close_pipe = close(pipe_write_fd);
    if (close_pipe == -1) {
        perror("unable to close pipe write end");
        exit(EXIT_FAILURE);
    }
    exit(EXIT_SUCCESS);
}

/**
 * Handle separate the work between process and merge the results
 */
void parallel_pipe_checker(int num_of_processes, const char *filename) {
    int worker_id;
    int pipefd[2];

    ResultStruct results = {0};
    ResultStruct results_buffer = {0};

    // initialize  pipe
    pipe(pipefd);

    pid_t pid;

    // Start num_of_processes new workers
    for (worker_id = 0; worker_id  < num_of_processes; ++worker_id ) {

        // TODO - fork the children and call worker_pipe_checker.
        // Possible implementation: Let worker_pipe_checker on which rows to perform work (from file).
        pid = fork();
        if(pi == -1) {
            perror("unable to pipe");
            exit(EXIT_FAILURE);
        }
        else if(pid == 0) {

            if(close(pipefd[0]) == -1) {
                perror("unable to close pipe read end");
                exit(EXIT_FAILURE);
            }
            worker_pipe_checker(worker_id, num_of_processes, filename, pipefd[1]);
            exit(EXIT_SUCCESS);
        }
    }

    for (worker_id = 0; worker_id  < num_of_processes; ++worker_id) {
        //sum the results
        if((read(pipefd[0], &results_buffer, sizeof(results_buffer))) == -1) {
            perror("unable to read from pipe");
            kill(0, SIGKILL);
            exit(EXIT_FAILURE);
        }
        results.sum += results_buffer.sum;
        results.unknown += results_buffer.unknown;
        results.amount += results_buffer.amount;
    }
    while(wait(NULL) > 0);

    // print the total results
    if(results.amount > 0) {
        printf("%.4f Average response time from %d sites, %d Unknown\n",
               results.sum / results.amount,
               results.amount,
               results.unknown);
    }
    else {
        printf("No Average response time from 0 sites, %d Unknown\n", results.unknown);
    }
}

int main(int argc, char **argv) {
    int pipe_flag = 0;
    if(argc == 4 && !strcmp(argv[3],"-f"))
    {
        pipe_flag = 1;
    }
    if (argc != 3 && !pipe_flag) {
        usage();
    } else if (atoi(argv[1]) == 1) {
        serial_checker(argv[2]);
    } else
    {
        if(pipe_flag)
        {
            parallel_pipe_checker(atoi(argv[1]), argv[2]);
        }
        else
        {
            parallel_mmap_checker(atoi(argv[1]), argv[2]);
        }
    }
    return EXIT_SUCCESS;
}

