package C1S.childgoodsstore.participants;

import C1S.childgoodsstore.entity.Participants;
import C1S.childgoodsstore.entity.Together;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {

    List<Participants> findByTogether(Together together);

}
