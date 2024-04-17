package C1S.childgoodsstore.tag.service;

import C1S.childgoodsstore.entity.Tag;
import C1S.childgoodsstore.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public List<String> getTag(String keyword){
        return tagRepository.findByTagStartingWith(keyword);
    }

}
