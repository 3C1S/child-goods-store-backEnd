package C1S.childgoodsstore.child.controller;

import C1S.childgoodsstore.child.dto.ChildResultDto;
import C1S.childgoodsstore.child.dto.ChildSaveDto;
import C1S.childgoodsstore.child.service.ChildService;
import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/child")
public class ChildController {


}
