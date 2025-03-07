package com.example.backend.service;

import com.example.backend.model.Note;
import com.example.backend.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository repository;

    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    public Note saveNote(Note note) {
        return repository.save(note);
    }
}
