# Chrono User Guide

Welcome to **Chrono**, your friendly command-line personal task manager! Chrono helps you keep track of **todos, deadlines, and events**, with full support for dates and times.

---

## Table of Contents
1. [Features](#features)
2. [Getting Started](#getting-started)
3. [Commands](#commands)
4. [Tips & Notes](#tips--notes)

---

## Features

- **Todo Tasks** – Keep track of tasks without dates.
- **Deadlines** – Track tasks that are due by a specific date and time.
- **Events** – Track tasks that have a start and end date/time.
- **Mark/Unmark Tasks** – Keep track of completed tasks.
- **Delete Tasks** – Remove tasks you no longer need.
- **Find Tasks** – Search tasks by keyword.
- **On Date Query** – See tasks happening and due on a particular date.
- **Persistent Storage** – All tasks are saved and loaded automatically.

---

## Getting Started

1. **Clone the repository** and open the project in VSCode.
2. **Run Chrono**:
   ```bash
   javac -d out src/main/java/chrono/*.java src/main/java/chrono/task/*.java src/main/java/chrono/exception/*.java
   java -cp out chrono.Chrono
   ```

---

## Commands

### 1. List all tasks
Shows all tasks currently in your list.

---

### 2. Add a todo
**Example:**
todo read book

---

### 3. Add a deadline
deadline <description> /by <date> <time>
**Example:**
deadline return book /by 2/12/2019 1800
- You can also use `today` instead of a full date:
deadline submit report /by today 1800

---

### 4. Add an event
event <description> /from <date/time> /to <date/time>
**Example:**
event birthday party /from 2/12/2019 1600 /to 2/12/2019 1800
- You can also use `today` for the current date:
event team meeting /from today 1400 /to today 1500

---

### 5. Mark a task as done
mark <task number>
**Example:**
mark 2

---

### 6. Unmark a task
unmark <task number>
**Example:**
unmark 2

---

### 7. Delete a task
delete <task number>
**Example:**
delete 3

---

### 8. Find tasks by keyword
find <keyword>
**Example:**
find book

---

### 9. Show tasks on a specific date
on <date>
**Example:**
on 02/12/2019
- Shows all deadlines and events occurring on that date.

---

## Tips & Notes

- Use the **exact date format** `dd/MM/yyyy HHmm` for deadlines and events.
- You can also use `today HHmm` to quickly add tasks for the current day.
- Tasks are automatically saved whenever you add, mark, unmark, or delete tasks.
- Remember: **task numbers start at 1**, as shown in the list command.
- Use the `find` command to quickly search tasks by keywords.
- Use the `on` command to check all tasks happening or due on a particular date.
