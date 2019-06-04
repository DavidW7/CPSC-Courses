package ca.ubc.cs.cpsc210.utility;

// Exception thrown when attempt is made to add a file (photo) that already exists to an album
class DuplicateFileException extends Exception {

    DuplicateFileException() {
        super();
    }

    DuplicateFileException(String msg) {
        super(msg);
    }
}
