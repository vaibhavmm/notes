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


    public void saveNote(NoteDto noteDto) {
        noteRepository.save(new Note(noteDto.getTitle(),noteDto.getContent()));
    }

    public List<Note> getNotes() {
        return noteRepository.findAllByStatus('A');
    }

    public Optional<Note> getNote(Long id) {
        return noteRepository.findById(id);
    }

    public void deleteNote(long id) {
        Optional<Note> note = noteRepository.findById(id);
        if(note.isPresent()){
            noteRepository.deleteById(id);
        }

    }
}
