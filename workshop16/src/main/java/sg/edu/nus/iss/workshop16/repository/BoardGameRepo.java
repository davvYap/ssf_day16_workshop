package sg.edu.nus.iss.workshop16.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Template;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.workshop16.model.Mastermind;

@Repository
public class BoardGameRepo {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public int saveGame(final Mastermind md) {
        redisTemplate.opsForValue().set(md.getId(), md.toJSON().toString());
        String result = (String) redisTemplate.opsForValue().get(md.getId());
        if (result != null) {
            return 1;
        }
        return 0;
    }

    public Mastermind findById(final String msId) throws IOException {
        String jsonStrVal = (String) redisTemplate
                .opsForValue()
                .get(msId);
        System.out.println("Find by id >>> " + jsonStrVal);
        Mastermind m = null;
        if (jsonStrVal != null) {
            m = Mastermind.createFromJSON(jsonStrVal);
            m.setId(msId);
        }
        return m;
    }

    public int updateBoardGamme(final Mastermind m) {
        String selectedResult = (String) redisTemplate.opsForValue().get(m.getId());

        if (m.isUpSert()) {
            if (selectedResult != null) {
                redisTemplate.opsForValue().set(m.getId(), m.toJSON().toString());
            } else {
                m.setId(m.generateId(8));
                redisTemplate.opsForValue().setIfAbsent(m.getId(), m.toJSON().toString());
            }
        } else {
            if (selectedResult != null) {
                redisTemplate.opsForValue().set(m.getId(), m.toJSON().toString());
            }
        }

        String finalResult = (String) redisTemplate.opsForValue().get(m.getId());
        if (finalResult != null)
            return 1;
        return 0;
    }

}
