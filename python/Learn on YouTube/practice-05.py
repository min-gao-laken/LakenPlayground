import random


def play_game(difficulty):
    # Configuration logic
    if difficulty == "easy":
        minNum, maxNum, maxGuesses = 1, 10, 5
    elif difficulty == "medium":
        minNum, maxNum, maxGuesses = 1, 50, 5
    elif difficulty == "hard":
        minNum, maxNum, maxGuesses = 1, 100, 5
    else:
        print("Invalid difficulty level. Setting to easy by default.")
        minNum, maxNum, maxGuesses = 1, 10, 5
        return

    # Variables
    # minNum = 1
    # maxNum = 100
    targetNumer = random.randint(minNum, maxNum)

    # maxGuesses = 3
    userGuessCount = 0

    while userGuessCount < maxGuesses:
        # Get input
        print(targetNumer)
        print(f"I am thinking of a number between {minNum} and {maxNum}")
        userGuess = int(input("Make a guess: "))
        userGuessCount += 1

        # Comparison logic
        if userGuess < targetNumer:
            print("Too low")
        elif userGuess > targetNumer:
            print("Too high")
        else:
            print("You Got It!")
            break

    if userGuessCount >= maxGuesses:
        print(f"You've run out of guesses, the number was {targetNumer}")


def main():
    while True:
        difficulty = input(
            "Choose a difficulty. Type 'easy', 'medium', or 'hard': ")
        play_game(difficulty)
        play_again = input("Do you want to play again? (y/n): ")
        if play_again.lower() != 'y':
            break


if __name__ == "__main__":
    main()
