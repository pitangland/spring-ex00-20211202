package org.zerock.service.project1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.project1.BoardVO;
import org.zerock.domain.project1.pageInfoVO;
import org.zerock.mapper.project1.BoardMapper;
import org.zerock.mapper.project1.FileMapper;
import org.zerock.mapper.project1.ReplyMapper;

import lombok.Setter;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class BoardService {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper replyMapper;
	
	@Setter(onMethod_ = @Autowired)
	private FileMapper fileMapper;
	
	@Value("${aws.accessKeyId}")
	private String accessKeyId;
	
	@Value("${aws.secretAccessKey}")
	private String secretAccessKey;
	
	@Value("${aws.bucketName}")
	private String bucketName;
	
	private Region region = Region.AP_NORTHEAST_2;
	
	private S3Client s3;
	
	private String staticRoot = "/Users/kim-wonpyo/Documents/course/fileUpload/board/";

	@PostConstruct
	public void init() {
		// spring bean이 만들어진 후 최초로 실행되는 코드 작성
		
		// 권한 정보 객체
		AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
		
		// crud 가능한 s3 client 객체 생성
		this.s3 = S3Client.builder()
				.credentialsProvider(StaticCredentialsProvider.create(credentials))
				.region(region)
				.build();
	
		System.out.println("#######s3client########");
		System.out.println(s3);
		
	}
	
	// s3에서 key로 객체 삭제
	private void deleteObject(String key) {
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
																	.bucket(bucketName)
																	.key(key).build();
		
		s3.deleteObject(deleteObjectRequest);
	}
	
	// s3에서 key로 객체 업로드(put)
	private void putObject(String key, Long size, InputStream source) {
		PutObjectRequest pubObjectRequest = PutObjectRequest.builder()
															.bucket(bucketName)
															.key(key)
															.acl(ObjectCannedACL.PUBLIC_READ)
															.build();
		
		RequestBody requestBody = RequestBody.fromInputStream(source, size);
		s3.putObject(pubObjectRequest, requestBody);
	}
	
	public boolean register(BoardVO board) {
		return mapper.insert(board) == 1;
	}
	
	public BoardVO get(Integer id) {
		return mapper.read(id);
	}
	
	public boolean modify(BoardVO board) {
		return mapper.update(board) == 1;
	}
	
	@Transactional
	public boolean remove(Integer id) {
		// 1. 게시물에 달린 댓글 지우기
		replyMapper.deleteByBoardId(id);
		
		// 2. file 지우기 
		String[] files = fileMapper.selectNamesByBoardId(id);
		
		if (files != null) {
			for (String file : files) {
				// 실제 file system 에서의 삭제
//				String path = staticRoot + id + "/" + file;
//				File target = new File(path);
//				
//				if (target.exists()) {
//					target.delete();
//				}
		
				// s3에서 삭제(aws)
				String key = "board/" + id + "/" + file;
				deleteObject(key);
								
			}
		}
		
		// db에서 삭제
		fileMapper.deleteByBoardId(id);
		
		// 3. 게시물 지우기
		return mapper.delete(id) == 1;		
	}
	
	public List<BoardVO> getList() {
		return mapper.getList();
	}

	public List<BoardVO> getListPage(Integer page, Integer numberPerPage) {
		
		// sql에서 사용할 record 시작 번호 (0-index)
		Integer from = (page-1) * 10;
		
		return mapper.getListPage(from, numberPerPage);
	}

	public pageInfoVO getPageInfo(Integer page, Integer numberPerPage) {
		// 총 게시물 수
		Integer countRows = mapper.getCountRows();
		// 마지막 페이지 번호
		Integer lastPage = (countRows - 1) / numberPerPage + 1;
		// 페이지네이션 가장 왼쪽 번호
		Integer leftPageNumber = (page-1)/10 * 10 + 1;
		// 페이지네이션 가장 오른쪽 번호
		Integer rightPageNumber = (page-1)/10 * 10 + 10;
		// 가장 마지막 페이지를 넘어가지 않도록..
		rightPageNumber = rightPageNumber > lastPage ? lastPage : rightPageNumber;
		// 이전 페이지 버튼 존재 유무
		Boolean hasPrevButton = leftPageNumber != 1;
		// 다음 페이지 버튼 존재 유무
		Boolean hasNextButton = rightPageNumber != lastPage;
				
		pageInfoVO pageInfo = new pageInfoVO();
		
		pageInfo.setLastPage(lastPage);
		pageInfo.setCountRows(countRows);
		pageInfo.setCurrentPage(page);
		pageInfo.setLeftPageNumber(leftPageNumber);
		pageInfo.setRightPageNumber(rightPageNumber);
		pageInfo.setHasPrevButton(hasPrevButton);
		pageInfo.setHasNextButton(hasNextButton);
		
		return pageInfo;
	}

	@Transactional
	public void register(BoardVO board, MultipartFile[] files) throws IllegalStateException, IOException {
		
		// 원래 하는일
		register(board);
		
//		// write files 하드디스크에 파일업로드
//		String basePath = staticRoot + board.getId();
//		if (files[0].getSize() > 0) {
//			// files가 있을 때만 폴더 생성
//			// 1. 새 게시물 id 이름의 folder 만들기
//			File newFolder = new File(basePath);
//			// 디렉토리를 만드는 메서드
//			newFolder.mkdirs();
//		}
//		// 2. 위 폴더에 files 쓰기
//		for (MultipartFile file : files) {
//			// 2.1 file 작성
//			if (file != null && file.getSize() > 0) {
//				String path = basePath + "/" + file.getOriginalFilename();
//				// Exception 처리를 해줌.
//				file.transferTo(new File(path));
//
//				// 2.2 insert into File(db에 작성)
//				fileMapper.insert(board.getId(), file.getOriginalFilename());
//			}		
//		}
		
		// 2. s3에 파일 업로드 (aws)
		for (MultipartFile file : files) {
			if (file != null && file.getSize() > 0) {
				// 2.1 file 작성, s3
				String key = "board/" + board.getId() + "/" + file.getOriginalFilename();
				putObject(key, file.getSize(), file.getInputStream());
				
				// 2.2 insert into File, DATABASE
				fileMapper.insert(board.getId(), file.getOriginalFilename());
			}
		}
		
	}

	public String[] getFileNamesByBoardId(Integer id) {
		
		return fileMapper.selectNamesByBoardId(id);
	}

	@Transactional
	public boolean modify(BoardVO board, String[] removeFile, MultipartFile[] files) throws IllegalStateException, IOException {
		// 원래 하던 일
		modify(board);
		
		String basePath = staticRoot + board.getId();
		
		// 파일 삭제
		if (removeFile != null) {
			for (String removeFileName : removeFile) {
				// file system에서 삭제
//				String path = basePath + "/" + removeFileName;
//				File target = new File(path);
//				
//				if (target.exists()) {
//					target.delete();
//				}
				
				// s3에서 삭제(aws)
				String key = "board/" + board.getId() + "/" + removeFileName;
				deleteObject(key);
				
				// db table에서 삭제
				fileMapper.delete(board.getId(), removeFileName);
				
			}			
		}
		
		// 새 파일 추가 하드디스크에 
//		if (files[0].getSize() > 0) {
//			// files가 있을 때만 폴더 생성
//			// 1. 새 게시물 id 이름의 folder 만들기
//			File newFolder = new File(basePath);
//			// 디렉토리를 만드는 메서드
//			newFolder.mkdirs();
//		}
		
		// 새파일 추가를 s3에 추가하는방법
		
		
		for (MultipartFile file : files) {
			if (file != null && file.getSize() > 0) {
				// 1. write file to fileSystem (하드디스크)
//				File newFile = new File(staticRoot + "/" + board.getId() + "/" + file.getOriginalFilename());
//				
//				if (!newFile.exists()) {
					// 2. db에 파일명 Insert
//					fileMapper.insert(board.getId(), file.getOriginalFilename());
//										
//				} 
//				file.transferTo(newFile);
				
				// 1. write file to s3 (s3 aws)
				String key = "board/" + board.getId() + "/" + file.getOriginalFilename();
				
				putObject(key, file.getSize(), file.getInputStream());
				
				// 2. db에 파일명 변경 (s3 aws)
				fileMapper.delete(board.getId(), file.getOriginalFilename());
				fileMapper.insert(board.getId(), file.getOriginalFilename());
			}
		}
		
		
		return false;
	}
	
	
	
}


















