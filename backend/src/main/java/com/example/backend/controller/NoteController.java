package com.example.backend.controller;

import com.example.backend.model.Note;
import com.example.backend.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "*")
public class NoteController {
    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Note> getNotes() {
        return service.getAllNotes();
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return service.saveNote(note);
    }
}
