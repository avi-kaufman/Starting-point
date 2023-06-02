import pygame
import random

# Define some colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)

# Set the width and height of the screen
SCREEN_WIDTH = 700
SCREEN_HEIGHT = 500

# Initialize Pygame
pygame.init()

# Set the screen size
screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))

# Set the caption for the screen
pygame.display.set_caption("Pong Game")

# Set the font for displaying score
font = pygame.font.Font(None, 36)

# Set the starting position of the ball
ball_x = SCREEN_WIDTH // 2
ball_y = SCREEN_HEIGHT // 2

# Set the starting speed of the ball
ball_speed_x = 0.2
ball_speed_y = 0.2

# Set the position and size of the paddles
player_paddle = pygame.Rect(50, SCREEN_HEIGHT // 2 - 50, 10, 100)
cpu_paddle = pygame.Rect(SCREEN_WIDTH - 60, SCREEN_HEIGHT // 2 - 50, 10, 100)

# Set the starting score for each player
player_score = 0
cpu_score = 0

# Main game loop
running = True
while running:
    # Handle events
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    # Move the player's paddle
    keys = pygame.key.get_pressed()
    if keys[pygame.K_UP]:
        if player_paddle.top > 0:
            player_paddle.move_ip(0, -1)
    elif keys[pygame.K_DOWN]:
        if player_paddle.bottom < SCREEN_HEIGHT:
            player_paddle.move_ip(0, 1)

        # Move the CPU's paddle
    if cpu_paddle.top < ball_y:
        if cpu_paddle.bottom < SCREEN_HEIGHT:
            cpu_paddle.move_ip(0, 1)
    elif cpu_paddle.bottom > ball_y:
        if cpu_paddle.top > 0:
            cpu_paddle.move_ip(0, -1)

    # Move the ball
    ball_x += ball_speed_x
    ball_y += ball_speed_y

    # Check for collision with the player's paddle
    if player_paddle.collidepoint(ball_x, ball_y):
        ball_speed_x *= -1

    # Check for collision with the CPU's paddle
    if cpu_paddle.collidepoint(ball_x, ball_y):
        ball_speed_x *= -1

    # Check for collision with the top or bottom of the screen
    if ball_y <= 0 or ball_y >= SCREEN_HEIGHT:
        ball_speed_y *= -1

    # Check if the ball has gone off the screen on the left or right side
    if ball_x <= 0:
        cpu_score += 1
        ball_x = SCREEN_WIDTH // 2
        ball_y = SCREEN_HEIGHT // 2
        ball_speed_x *= random.choice([-1, 1])
        ball_speed_y *= random.choice([-1, 1])
    elif ball_x >= SCREEN_WIDTH:
        player_score += 1
        ball_x = SCREEN_WIDTH // 2
        ball_y = SCREEN_HEIGHT // 2
        ball_speed_x *= random.choice([-1, 1])
        ball_speed_y *= random.choice([-1, 1])

    # Clear the screen
    screen.fill(BLACK)

    # Draw the player's paddle
    pygame.draw.rect(screen, WHITE, player_paddle)

    # Draw the CPU's paddle
    pygame.draw.rect(screen, WHITE, cpu_paddle)

    # Draw the ball
    pygame.draw.circle(screen, WHITE, (ball_x, ball_y), 10)

    # Draw the score
    player_text = font.render("Player: " + str(player_score), True, WHITE)
    cpu_text = font.render("CPU: " + str(cpu_score), True, WHITE)
    screen.blit(player_text, (50, 10))
    screen.blit(cpu_text, (SCREEN_WIDTH - 150, 10))

    # Update the screen
    pygame.display.flip()
