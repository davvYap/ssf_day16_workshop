package sg.edu.nus.iss.workshop16.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.workshop16.model.Mastermind;
import sg.edu.nus.iss.workshop16.repository.BoardGameRepo;

@Service
public class BoardGameService {
    @Autowired
    private BoardGameRepo boardGameRepo;

    public int saveGame(final Mastermind md) {
        return boardGameRepo.saveGame(md);
    }

    public Mastermind findbyId(final String msId) throws IOException {
        return boardGameRepo.findById(msId);
    }

    public int updateBoardGamme(final Mastermind m) {
        return boardGameRepo.updateBoardGamme(m);
    }
}
