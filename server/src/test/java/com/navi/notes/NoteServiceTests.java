package com.navi.notes;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.navi.notes.domain.Note;
import com.navi.notes.dto.NoteDto;
import com.navi.notes.repo.NoteRepository;
import com.navi.notes.service.NoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NoteServiceTests {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    public void setup() {
        noteRepository.deleteAllInBatch();
    }

    @Test
    public void saveNoteTest() {
        noteService.saveNote(new NoteDto("My first Test", "Im testing noteservice using junit. P:S: Naveen is a Thanda case."));
        List<Note> notes = noteRepository.findAll();
        assertEquals(1, notes.size());
        assertEquals("My first Test", notes.get(0).getTitle());
        assertEquals("Im testing noteservice using junit. P:S: Naveen is a Thanda case.", notes.get(0).getContent());
        assertEquals('A', notes.get(0).getStatus());
    }

    @Test
    public void getNotesTest() {
        NoteDto note1 = new NoteDto("My first Test", "Im testing noteservice using junit. P:S: Naveen is a Thanda case.");
        noteService.saveNote(note1);
        NoteDto note2 = new NoteDto("Testing", "Testing getnote. One note at a time");
        noteService.saveNote(note2);
        List<Note> notes = noteService.getNotes();
        assertEquals(2, notes.size());
        List<NoteDto> noteDtos = Arrays.asList(note1, note2);
        for (NoteDto noteDto : noteDtos) {
            Optional<Note> noteOptional = notes.stream()
                    .filter(note -> note.getTitle().equals(noteDto.getTitle())
                            && note.getContent().equals(noteDto.getContent()))
                    .findFirst();
            Assertions.assertTrue(noteOptional.isPresent());
            assertEquals('A',noteOptional.get().getStatus() );

        }
    }

    @Test
    public void getNoteTest(){
        NoteDto noteDto = new NoteDto("My first Test", "Im testing noteservice using junit. P:S: Naveen is a Thanda case.");
        Note saveNote = noteService.saveNote(noteDto);
        Optional<Note> noteOptional = noteService.getNote(saveNote.getId());
        Assertions.assertTrue(noteOptional.isPresent());
        assertEquals(noteDto.getTitle(),noteOptional.get().getTitle());
        assertEquals(noteDto.getContent(),noteOptional.get().getContent());
        assertEquals('A',noteOptional.get().getStatus());
    }

    @Test
    public void deleteNoteTest(){
        NoteDto noteDto = new NoteDto("My first Test", "Im testing noteservice using junit. P:S: Naveen is a Thanda case.");
        Note saveNote = noteService.saveNote(noteDto);
        noteService.deleteNote(saveNote.getId());
        List<Note> deletedNotes = noteRepository.findAllByStatus('D');
        assertEquals(1,deletedNotes.size());
        assertEquals(noteDto.getTitle(),deletedNotes.get(0).getTitle());
        assertEquals(noteDto.getContent(),deletedNotes.get(0).getContent());
    }

    @Test
    public void updateNoteTest(){
        NoteDto newNoteDto = new NoteDto("My first Test", "Im testing noteservice using junit. P:S: Naveen is a Thanda case.");
        Note savedNote = noteService.saveNote(newNoteDto);
        NoteDto updateNoteDto = new NoteDto("updation", "Im testing update case.");
        noteService.updateNote(savedNote.getId(),updateNoteDto);
        Optional<Note> noteOptional = noteRepository.findById(savedNote.getId());
        assertTrue(noteOptional.isPresent());
        assertEquals(updateNoteDto.getTitle(),noteOptional.get().getTitle());
        assertEquals(updateNoteDto.getContent(),noteOptional.get().getContent());

    }
}
