package com.navi.notes.controller;

import com.navi.notes.domain.Note;
import com.navi.notes.dto.NoteDto;
import com.navi.notes.service.NoteService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping
    public List<Note> getNotes(){

        return noteService.getNotes();
    }
    @GetMapping("/{id}")
    public Optional<Note> getNote(@PathVariable("id") long id){
        return  noteService.getNote(id);
    }
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") long id){
        noteService.deleteNote(id);
    }

    @PostMapping
    public ResponseEntity saveNote(@RequestBody NoteDto noteDto){
        noteService.saveNote(noteDto);
        return ResponseEntity.ok().build();
    }
}
