# Chrono User Guide

Welcome to **Chrono**, your command-line personal task manager that helps you manage tasks, deadlines, and events efficiently using simple text-based commands.

---

## Table of Contents

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [Command Summary](#command-summary)
4. [Commands](#commands)
   - [Viewing tasks: `list`](#viewing-tasks-list)
   - [Adding a todo: `todo`](#adding-a-todo-todo)
   - [Adding a deadline: `deadline`](#adding-a-deadline-deadline)
   - [Adding an event: `event`](#adding-an-event-event)
   - [Marking a task as done: `mark`](#marking-a-task-as-done-mark)
   - [Unmarking a task: `unmark`](#unmarking-a-task-unmark)
   - [Deleting a task: `delete`](#deleting-a-task-delete)
   - [Finding tasks by keyword: `find`](#finding-tasks-by-keyword-find)
   - [Finding tasks by date: `on`](#finding-tasks-by-date-on)
   - [Exiting the program: `bye`](#exiting-the-program-bye)
5. [FAQ](#faq)
6. [Known Issues](#known-issues)
7. [Command Summary Table](#command-summary-table)

---

## Quick Start

1. Ensure you have Java 11 or above installed on your computer.
2. Download the latest `chrono.jar` from [here](link-to-release).
3. Copy the file to the folder you want to use as the home folder for Chrono.
4. Open a command terminal, navigate to the folder containing `chrono.jar`, and run:
   ```
   java -jar chrono.jar
   ```
5. Type commands in the terminal and press Enter to execute them.
6. Refer to the [Commands](#commands) section below for details of each command.

---

## Features

Chrono supports:

- **Todo tasks** — simple tasks with no dates
- **Deadline tasks** — tasks with a due date and time
- **Event tasks** — tasks that happen between a start and end time
- **Task searching** — by keyword or by date
- **Persistent storage** — saves tasks across runs
- **Simple interaction** — all via typed commands

---

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| **List** | `list` | `list` |
| **Todo** | `todo DESCRIPTION` | `todo read book` |
| **Deadline** | `deadline DESCRIPTION /by DATE TIME` | `deadline return book /by 2/12/2019 1800` |
| **Event** | `event DESCRIPTION /from DATE TIME /to DATE TIME` | `event meeting /from 2/12/2019 1400 /to 2/12/2019 1600` |
| **Mark** | `mark INDEX` | `mark 2` |
| **Unmark** | `unmark INDEX` | `unmark 2` |
| **Delete** | `delete INDEX` | `delete 3` |
| **Find** | `find KEYWORD` | `find book` |
| **On** | `on DATE` | `on 02/12/2019` |
| **Exit** | `bye` | `bye` |

---

## Commands

> **Notes about command format:**
> * Words in `UPPER_CASE` are parameters to be supplied by you.
>   * Example: In `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo read book`.
> * Parameters must be in the specified order.
> * Items in square brackets are optional.
> * Date format: `dd/MM/yyyy` (e.g., `05/12/2019`)
> * Time format: `HHmm` in 24-hour format (e.g., `1800` for 6:00 PM)
> * You can use `today` as a shortcut for the current date

---

### Viewing tasks: `list`

Shows a list of all tasks in Chrono.

**Format:** `list`

**Example:**

```
list
```

**Expected output:**

```
Here are the tasks in your list:
1. [T][ ] read book
2. [D][X] return book (by: Monday, 2 Dec 2019 6:00 PM)
3. [E][ ] project meeting (from: Monday, 2 Dec 2019 2:00 PM to: Monday, 2 Dec 2019 4:00 PM)
```

**Legend:**
- `[T]` = Todo task
- `[D]` = Deadline task
- `[E]` = Event task
- `[X]` = Task is marked as done
- `[ ]` = Task is not done yet

---

### Adding a todo: `todo`

Adds a todo task to your task list. Todo tasks are simple tasks without any date or time attached.

**Format:** `todo DESCRIPTION`

**Example:**

```
todo read book
```

> **💡 Tip:** Use todos for tasks that don't have a specific deadline or time frame.

---

### Adding a deadline: `deadline`

Adds a deadline task with a due date and time.

**Format:** `deadline DESCRIPTION /by DATE TIME`

**Example:**

```
deadline return book /by 2/12/2019 1800
```

> **⚠️ Important:** The `/by` keyword is required to separate the description from the date/time.

---

### Adding an event: `event`

Adds an event task that occurs within a specific time period.

**Format:** `event DESCRIPTION /from DATE TIME /to DATE TIME`

**Example:**

```
event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600
```

> **⚠️ Important:** Both `/from` and `/to` keywords are required.

---

### Marking a task as done: `mark`

Marks the specified task as completed.

**Format:** `mark INDEX`

- `INDEX` refers to the index number shown in the displayed task list
- The index must be a positive integer (1, 2, 3, ...)

**Example:**

```
mark 2
```

> **💡 Tip:** Use `list` first to see the index numbers of your tasks.

---

### Unmarking a task: `unmark`

Marks the specified task as not done.

**Format:** `unmark INDEX`

- `INDEX` refers to the index number shown in the displayed task list
- The index must be a positive integer (1, 2, 3, ...)

**Example:**

```
unmark 2
```

---

### Deleting a task: `delete`

Deletes the specified task from your task list.

**Format:** `delete INDEX`

- `INDEX` refers to the index number shown in the displayed task list
- The index must be a positive integer (1, 2, 3, ...)

**Example:**

```
delete 3
```

> **⚠️ Warning:** This action cannot be undone. The task will be permanently removed.

---

### Finding tasks by keyword: `find`

Finds all tasks whose descriptions contain the specified keyword.

**Format:** `find KEYWORD`

- The search is case-sensitive
- Only tasks with descriptions containing the keyword will be returned
- Only full word matches are considered

**Example:**

```
find book
```

> **💡 Tip:** If no tasks match your keyword, Chrono will display "No matching tasks found."

---

### Finding tasks by date: `on`

Shows all deadlines and events occurring on the specified date.

**Format:** `on DATE`

- Date format must be `dd/MM/yyyy`
- Only deadlines and events are shown (todos have no dates)


**Example:**

```
on 02/12/2019
```

> **💡 Tip:** Use this command to plan your day and see what's due.

---

### Exiting the program: `bye`

Exits the Chrono application.

**Format:** `bye`

**Example:**

```
bye
```

> **Note:** All your tasks are automatically saved before exiting.

---

## FAQ

**Q: How do I transfer my data to another computer?**
A: Install Chrono on the other computer and copy the `data/tasks.txt` file from your old computer to the same location on the new computer.

**Q: Can I edit the data file directly?**
A: Yes, advanced users can edit `data/tasks.txt` directly. However, be careful as incorrect formatting may cause Chrono to not load your tasks properly. It's recommended to make a backup before editing.

**Q: Is there a limit to how many tasks I can add?**
A: No, there is no hard limit on the number of tasks. However, very large task lists may affect performance.

---

## Known Issues

1. **Date parsing limitation**: Currently, Chrono only accepts dates in `dd/MM/yyyy` format. Other common formats like `MM/dd/yyyy` or `yyyy-MM-dd` are not supported.

2. **Time zone**: All times are stored and displayed in your system's local time zone.

3. **Long descriptions**: Task descriptions that are extremely long (over 200 characters) may not display properly in some terminal windows.

4. **Special characters**: Using special characters like `|` or `/` in task descriptions (other than the command separators `/by`, `/from`, `/to`) may cause parsing errors.

---

## Command Summary Table

| Action | Format | Examples |
|--------|--------|----------|
| **Add Todo** | `todo DESCRIPTION` | `todo read book` |
| **Add Deadline** | `deadline DESCRIPTION /by DATE TIME` | `deadline return book /by 2/12/2019 1800`<br>`deadline homework /by today 2359` |
| **Add Event** | `event DESCRIPTION /from DATE TIME /to DATE TIME` | `event meeting /from 2/12/2019 1400 /to 2/12/2019 1600`<br>`event party /from today 1800 /to today 2200` |
| **List Tasks** | `list` | `list` |
| **Mark Done** | `mark INDEX` | `mark 1` |
| **Mark Not Done** | `unmark INDEX` | `unmark 1` |
| **Delete Task** | `delete INDEX` | `delete 2` |
| **Find by Keyword** | `find KEYWORD` | `find book` |
| **Find by Date** | `on DATE` | `on 02/12/2019` |
| **Exit** | `bye` | `bye` |

---

**Enjoy using Chrono to organize your life one task at a time!**
