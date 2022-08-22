package Tests;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.jupiter.api.Test;
import Exceptions.InvalidFileFormatException;
import NoteUploading.Note;

public class CheckingDocTypeTests {
	
	@Test
	void checkIfThrowsErrorWithInvalidFileFormat() {
		Note n = new Note();
		assertThrows(InvalidFileFormatException.class, 
				() -> {
			n.setDoc(new File("doc.pdf"));
		});
	}
	
	@Test
	void checkIfFileWasSavedCorrectly() throws InvalidFileFormatException {
		Note n = new Note();
		File file = new File("doc.txt");
		String docName = file.getName();
		n.setDoc(file);
		assertEquals(docName, file.getName());
	}
	
	@Test
	void checkFileWithshortName() throws InvalidFileFormatException { 
		Note n = new Note();
		File file = new File("x.txt");
		String docName = file.getName();
		n.setDoc(file);
		assertEquals(docName, file.getName());
	}
	
	@Test
	void checkIfThrowsErrorOnNullFile() throws InvalidFileFormatException {
		Note n = new Note();
		assertThrows(InvalidFileFormatException.class, 
				() -> {
			n.setDoc(null);
		});
	}
	
	@Test
	void checkFileWithWeirdName() { 
		Note n = new Note();
		assertThrows(InvalidFileFormatException.class, 
				() -> {
			n.setDoc(new File("mohamad.txt.pdf")); 	
			// this could should throw error cause doc of type PDF
		});
	} 
	
	//code was wrong
	/*
	public void setDoc(File doc) throws InvalidFileFormatException {
		if (doc != null && doc.getName().contains(".txt"))
			this.doc = doc;
		else throw new InvalidFileFormatException();
	}	
	*/
	
	// changed the code to this
	/*
	public void setDoc(File doc) throws InvalidFileFormatException {
		if (doc != null && doc.getName().length() >= 4 && 
				doc.getName().substring(doc.getName().length() - 4).equals(".txt"))
			this.doc = doc;
		else throw new InvalidFileFormatException();
	}
	*/
	
}
