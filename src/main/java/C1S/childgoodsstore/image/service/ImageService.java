package C1S.childgoodsstore.image.service;

import C1S.childgoodsstore.image.dto.ImageDto;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String bucketName = "childbucket";
    private final AmazonS3Client amazonS3Client;

    // controller - MultipartFile 객체 리스트를 S3에 저장 후 URL 리스트 반환
    public List<String> saveImageList(ImageDto imageDto) {
        List<String> imageUrlList = new ArrayList<>();
        for(MultipartFile multipartFile : imageDto.getImageList()) {
            // 요청 폴더에 상품 이미지 저장
            String value = saveImage(multipartFile, imageDto.getFolderName());
            imageUrlList.add(value);
        }
        // 이미지 URL 리스트 반환
        return imageUrlList;
    }

    // MultipartFile 객체와 디렉토리 경로를 받아 파일을 Amazon S3에 저장
    public String saveImage(MultipartFile multipartFile, String directoryPath) {
        // 디렉토리 경로를 포함하여 파일 이름을 생성합니다.
        String fileName = directoryPath + "/" + createStoreFileName(multipartFile.getOriginalFilename());

        try {
            // 업로드할 파일의 메타데이터 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            // Amazon S3에 파일 업로드
            amazonS3Client.putObject(bucketName, fileName, multipartFile.getInputStream(), objectMetadata);

            // 업로드된 파일의 URL 반환
            return amazonS3Client.getUrl(bucketName, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 주어진 파일 URL에 해당하는 파일을 Amazon S3에서 삭제
    public void deleteImage(String fileUrl) {
        // 파일 URL에서 파일 이름 추출
        String splitStr = ".com/";
        String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());

        // Amazon S3에서 해당 파일을 삭제 요청
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    // 주어진 원본 파일 이름에서 확장자를 추출하여 유니크한 파일 이름 생성
    private String createStoreFileName(String originalFilename) {
        return UUID.randomUUID() + "." + extractExt(originalFilename);
    }
    // 주어진 원본 파일 이름에서 확장자 부분만 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}