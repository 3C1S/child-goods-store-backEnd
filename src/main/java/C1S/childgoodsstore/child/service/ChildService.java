package C1S.childgoodsstore.child.service;

import C1S.childgoodsstore.child.dto.ChildResultDto;
import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.child.repository.ChildRepository;
import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class ChildService {

    private final ChildRepository childRepository;


    public ChildResultDto save(User user, ChildSaveDto childDto) {
        Child child = childRepository.saveAndFlush(new Child(user, childDto));

        return new ChildResultDto(child);
    }
}
