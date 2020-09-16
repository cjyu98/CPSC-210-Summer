# My Personal Project: A Habit Tracker

## Why a Habit Tracker?
We all have habits we try to break/introduce into our lives. Whether it is that New Year's Resolution's goal of going to the gym three times a week or trying to get 8 hours of sleep every night, introducing permanent change in our lives can be tricky and hard to quantify the results. Using a habit tracker has a lot of benefits, some of which include: 
- visualization of tracking your behaviours/patterns 
- offer extra motivation to keep up your streak
- being more mindful of your actions
- self-accountablity

## Who can use it?
Anyone and everyone can use it! Whether you simply wish to track one or twenty habits at once, everyone has something they can work on changing in their lives for their own self-improvement, regardless what area of their life they are working on. We're all human and can always improve. 

## Why make a Habit Tracker?
Personally, I've always had issues keeping momentum of change I try to introduce into my life. Whether it is trying to pick up a new workout routine or just remembering to do a simple task before bed, it is all too easy to relax back into my old lazy ways. In twelfth grade, I had picked up bullet journalling as a means to try and improve my productivity for the increasing workload at school and came across this idea of habit tracking. I began tracking this through my own process of journalling via pen and paper with varying degrees of success over the years. One of the big problems I ran into was that I could not feasibly carry a journal everywhere to remind me and drawing the charts became extremely time consuming. Hence the need for a digital version was born and formed the basis of this project for me.

*Also because I was way too cheap to pay for an iPhone app for this but somehow not lazy enough to decide to code this.*

## User Stories

- As a user, I want to be able to add/remove a habit that I wish to track
- As a user, I want to be able to view my entire list of habits that I am tracking
- As a user, I want to be able to be able to mark the completion of each habit every day
- As a user, I want to be able to sort and prioritize the list of habits

- As a user, I want to be able to save my list of habits before exiting the application
- As a user, I want to be able to load my list every time I open the application

## Instruction for Graders

- You can add a habit by typing in the name of the habit in the text field at the bottom and click the add button
- You can remove a habit by selecting a habit and clicking the remove button
- The audio component can be triggered by clicking any button (remove, add, save) creating a clicking sound
- The list automatically loads from the last save file
- You can save the list by clicking the save button


## Phase 4

### Task 2 
- ListOfHabits throws an exception "NotInList" which is tested and caught in ListOfHabitTest for methods
getHabitFromName and getHabitFromIndex, these methods are commonly used so other tests throw the same exception.

### Task 3
- refactor repetitive while loops in ListOfHabits for methods getHabitByName,removeByName,moveInPositionInList into
a new method
- refactor repetitive code for initializing buttons in GUI in DisplayList into a new initializeButton method


