package sg.edu.nus.iss.workshop16.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.workshop16.model.Mastermind;
import sg.edu.nus.iss.workshop16.service.BoardGameService;

@RestController
@RequestMapping(path = "/api/boardgame", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardGameController {
    @Autowired
    private BoardGameService boardGameService;

    @PostMapping
    public ResponseEntity<String> createBoardGame(@RequestBody Mastermind m) {
        int insertCount = boardGameService.saveGame(m);
        Mastermind result = new Mastermind();
        result.setId(m.getId()); // the id will be auto generated once instantiated
        result.setInsertCount(insertCount);

        // this is optional, by Kenneth
        if (insertCount == 0) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toJSONInsert().toString());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toJSONInsert().toString());
    }

    // get the json string from id
    @GetMapping(path = "{msId}")
    public ResponseEntity<String> getBoardGame(@PathVariable String msId) throws IOException {
        Mastermind m = boardGameService.findbyId(msId);
        if (m == null || m.getName() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("null");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(m.toJSON().toString());
    }

    // update json in database
    @PutMapping(path = "{msId}")
    public ResponseEntity<String> updateBoardGame(
            @RequestBody Mastermind m,
            @PathVariable String msId,
            @RequestParam(defaultValue = "false") boolean isUpSert) throws IOException {
        Mastermind result = null;
        m.setUpSert(isUpSert);
        // if upsert is false and msId is null, then return 400 status code

        result = boardGameService.findbyId(msId);
        System.out.println("Result >>> " + result);
        if (!isUpSert) {
            if (result == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("null");
            }
        } else {
            // if upsert is true but msId is null, then perform insert
            if (result == null) {
                m.setId(msId);
                int newInsertCount = boardGameService.saveGame(m);
                m.setInsertCount(newInsertCount);
                System.out.printf("%s >>> INSERTED.\n", msId);

                return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(m.toJSONInsert().toString());
            }
        }

        // if upsert is false but msId is not null OR upsert is true and msId is not
        // null, then perform update
        m.setId(msId);
        int updateCnt = boardGameService.updateBoardGamme(m);
        m.setUpdateCount(updateCnt);
        System.out.printf("%s >>> UPDATED.\n", msId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(m.toJSONUpdate().toString());

    }
}
