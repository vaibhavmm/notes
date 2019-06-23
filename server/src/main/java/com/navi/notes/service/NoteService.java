package com.navi.notes.service;

import com.navi.notes.dto.NoteDto;
import com.navi.notes.repo.NoteRepository;
import com.navi.notes.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;


    public Note saveNote(NoteDto noteDto) {
        return noteRepository.save(new Note(noteDto.getTitle(),noteDto.getContent()));
    }

    public List<Note> getNotes() {
        return getNotesByStatus('A');
    }

    private List<Note> getNotesByStatus(char status) {
        return noteRepository.findAllByStatus(status);
    }

    public Optional<Note> getNote(Long id) {
        return noteRepository.findById(id);
    }

    public void deleteNote(long id) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if(noteOptional.isPresent()){
            Note note = noteOptional.get();
            note.setStatus('D');
            noteRepository.save(note);
        }

    }

    public Note updateNote(long id, NoteDto noteDto) {
        Optional<Note> noteOptional = noteRepository.findById(id);
        if(noteOptional.isPresent()){
            Note note = noteOptional.get();
            note.setTitle(noteDto.getTitle());
            note.setContent(noteDto.getContent());
            return noteRepository.save(note);
        }else{
            throw new RuntimeException(String.format("Note with Id %d not found", id) );
        }
    }
}
