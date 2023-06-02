/*
 * ex1.c
 name: Avi Kaufman, ID:205462591
 */

#include <sys/types.h>
#include <sys/stat.h>

#include <fcntl.h>
#include <getopt.h>
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define MAX_BUFFER_SIZE 65536
#define DESTINATION_FILE_MODE S_IRUSR|S_IWUSR|S_IRGRP|S_IROTH

extern int opterr, optind;
char reverse_buffer[MAX_BUFFER_SIZE] = {0};

void exit_with_usage(const char *message) {
	fprintf (stderr, "%s\n", message);
	fprintf (stderr, "Usage:\n\tex1 [-f] BUFFER_SIZE SOURCE DEST\n");
	exit(EXIT_FAILURE);
}

off_t read_stream_reverse(FILE *stream, char *buf, size_t count, bool *mark_end) {
	/*
		Reads from stream in reverse order into buf 'count' bytes.
	*/
	long original_position = 0;
	long end_position = 0;
	int i = 0, temp = 0;
	
	original_position = ftell(stream);
	end_position = original_position - count;
	if (end_position < 0) {
		end_position = -1;
		*mark_end = true;
	}
	count = original_position - end_position;
	fseek(stream, end_position + 1, SEEK_SET);
	
	temp = fread(reverse_buffer, 1, count, stream);
	if (temp != count) {
	 	 printf("Unable to read source file contents\n");
		 exit(EXIT_FAILURE);
	}
	i = count - 1;
	while (i >= 0) {
		buf[count-i-1] = reverse_buffer[i];
		i--;
	}

	if (*mark_end == true) {
		fseek(stream, 0, SEEK_SET);
	}
	else {
		fseek(stream, end_position, SEEK_SET);
	}
	return count;
}

void append_file_reverse(const char *source_file, const char *dest_file, int buffer_size, int max_size, int force_flag) {
	/*
	 * Append source_file content to dest_file, buffer_size bytes at a time, in reverse order (start from the end of 'source_file').
	 * If source_file is larger than 'max_size', then only 'max_size' bytes will be written.
	 * NOTE: If max_size is smaller or equal to 0, then max_size should be IGNORED.
	 * If force_flag is true, then also create dest_file if does not exist. Otherwise (if force_flag is false, and dest_file doesn't exist) print error, and exit.
	 *
	 * Examples:
	 * 1. if source_file's content is 'ABCDEFGH', 'max_size' is 3 and dest_file doesnt exist then
	 *	  dest_file's content will be 'HGF' (3 bytes from the end, in reverse order)
	 * 2. if source_file's content is '123456789', 'max_size' is -1, and dest_file's content is: 'HGF' then
	 *	  dest file's content will be 'HGF987654321'
	 *
	 * TODO:
	 * 	1. Open source_file for reading 
	 *	2. Reposition the read file offset of source_file to the end of file (minus 1).
	 * 	3. Open dest_file for writing (Hint: is force_flag true?)
	 * 	4. Loop read_reverse from source and writing to the destination buffer_size bytes each time (lookout for mark_end)
	 * 	5. Close source_file and dest_file
	 *
	 *  ALWAYS check the return values of syscalls for errors!
	 *  If an error was found, use perror(3) to print it with a message, and then exit(EXIT_FAILURE)
	 */
	 
	 //**************************************************************************************************************************
	 //1. Open source_file for reading
	 int fd_source = open(source_file, O_RDONLY);
	 if (fd_source < 0) {
	    perror("Unable to open source file for reading"); 
	    exit(EXIT_FAILURE);
	 }
	 
	 
	 //2. Reposition the read file offset of source_file to the end of file (minus 1).
	 int reposition = lseek (fd_source, -1, SEEK_END);
	 if (reposition < 0) {
	    perror("Unable to reposition the read file"); 
	    exit(EXIT_FAILURE);
	 }
	 
	 
	 //3. Open dest_file for writing (Hint: is force_flag true?)
	 int fd_dest = open(dest_file, O_WRONLY);
	 if ((fd_dest < 0) && (force_flag == true)) {
         fd_dest = open(dest_file, O_WRONLY | O_CREAT | O_EXCL);

         if (dest_file < 0) {
             perror("Unable to open destination file for writing");
             exit(EXIT_FAILURE);
         }
     }
	 if (fd_dest<0){
	    perror("Unable to open destination file for writing");
	    exit(EXIT_FAILURE);
	 }
     FILE * file = fdopen(fd_source,"r");
	 /* check if FILE file managed o open*/
	 
	 //4. Loop read_reverse from source and writing to the destination buffer_size bytes each time (lookout for mark_end)
	 bool mark_end = false;
	 char str[MAX_BUFFER_SIZE];
	 while (!mark_end) {
	     int read_from_source = read_stream_reverse(file, str, buffer_size, &mark_end);
	     
	     if(read_from_source < 0){
	        perror("Unable to read source file");
	        exit(EXIT_FAILURE);
	     }
	     write(fd_dest, str, read_from_source);
	 }
	    
     
     
     //5. Close source_file and dest_file
     if (close(fd_source) == -1) {
        perror("Unable to close source file"); 
        exit(EXIT_FAILURE); 
    }
    
    if (close(fd_dest) == -1) {
        perror("Unable to close destination file  "); 
        exit(EXIT_FAILURE); 
    }
    printf("Content from file %s   was successfully appended (in reverse) to  %s", source_file, dest_file);
    
	 
}
//******************************************************************************************************************************************
void parse_arguments(
		int argc, char **argv,
		char **source_file, char **dest_file, int *buffer_size, int *force_flag) {
	/*
	 * parses command line arguments and set the arguments required for append_file
	 */
	int option_character;

	opterr = 0; /* Prevent getopt() from printing an error message to stderr */

	while ((option_character = getopt(argc, argv, "f")) != -1) {
		switch (option_character) {
		case 'f':
			*force_flag = 1;
			break;
		default:  /* '?' */
			exit_with_usage("Unknown option specified");
		}
	}

	if (argc - optind != 3) {
		exit_with_usage("Invalid number of arguments");
	} else {
		*source_file = argv[argc-2];
		*dest_file = argv[argc-1];
		*buffer_size = atoi(argv[argc-3]);

		if (strlen(*source_file) == 0 || strlen(*dest_file) == 0) {
			exit_with_usage("Invalid source / destination file name");
		} else if (*buffer_size < 1 || *buffer_size > MAX_BUFFER_SIZE) {
			exit_with_usage("Invalid buffer size");
		}
	}
}

int main(int argc, char **argv) {
	int force_flag = 0; /* force flag default: false */
	char *source_file = NULL;
	char *dest_file = NULL;
	int buffer_size = MAX_BUFFER_SIZE;

	parse_arguments(argc, argv, &source_file, &dest_file, &buffer_size, &force_flag);

	append_file_reverse(source_file, dest_file, buffer_size, 8, force_flag);

	return EXIT_SUCCESS;
}

