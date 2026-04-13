# SOEN_342_Group_Project

Iteration 00

## Team

| Name | Student ID | GitHub Username |
|------|------------|-----------------|
| Aboudraz, Reema | 40253549 | reemaaboudraz |
| Mandamiento Rodriguez, Theo-Dayan | 40310410 | t_mandam |
| Seddaoui, Lyne | 40252125 | lynesdd |

## Instructor Quick Start

This project is a console-based task management system. Tutors and professors can use it to demonstrate task creation, project tracking, collaborator assignment, search, import/export, and status updates from the terminal.

### Initialize the software

1. Open the `code/` folder in VS Code.
2. Run `src/main/java/com/taskmanagement/PersonalTaskManagementSystem.java` directly from the editor using the Java run action.
3. If you want to rebuild the application from source, use Maven from the `code/` folder:

	```bash
	mvn clean package
	```

4. If you prefer to start the packaged jar after rebuilding, run:

	```bash
	java -jar target/task-management-system-1.0.0.jar
	```

The first launch automatically creates the SQLite database and schema if they do not already exist. The database file is stored in `code/data/taskmanagement.db`, so running the program from the `code/` folder keeps the data in the expected location.

If the project is already compiled in your workspace, Java 21 is enough to launch it directly from VS Code without installing JDK 11.

### Use the software

When the program starts, it opens an interactive command-line interface. Type `help` to see the available commands and `help <command-name>` for command-specific usage.

Common commands include:

- `create-task`
- `update-task`
- `create-tag`
- `create-project`
- `list-projects`
- `add-task-to-project`
- `create-collaborator`
- `assign-collaborator`
- `list-collaborators`
- `list-assignments`
- `activity-log`
- `search-task`
- `sort-task`
- `import`
- `export`

Useful classroom examples:

- Create a project: `create-project Final Project | Group deliverable for the semester`
- Create a task: `create-task regular Draft proposal | Prepare the project proposal`
- Add a tag: `create-tag urgent`
- Update a task: `update-task 1 status complete`
- Search tasks: `search-task keyword proposal | status OPEN`
- Show help for one command: `help create-task`

Type `exit` or `quit` to close the application.
