package com.xmcc;

import com.xmcc.model.Student;
import com.xmcc.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)//spring整合jiunit测试需要的东西
@SpringBootTest
public class WxSellApplicationTests {

	@Autowired
	private StudentRepository studentRepository;

	@Test
	public void contextLoads() {
		List<Student> all = studentRepository.findAll();
		/*all.stream().forEach(System.out::println);*/

		/*studentRepository.save(new Student(null,"上火","男","20"));*/

		/*studentRepository.deleteById(8);*/

		/*studentRepository.saveAndFlush(new Student(7,"上火","男","21"));*/

		/*ArrayList<Integer> ids = new ArrayList<>();
		ids.add(1);
		ids.add(2);
		ids.add(6);
		List<Student> allByIdIn = studentRepository.findAllByIdIn(ids);
		allByIdIn.stream().forEach(System.out::println);*/

		/*Student student = studentRepository.queryStudentByStudentId(2);
		System.out.println(student);*/

		Student student1 = studentRepository.studentByStudentId(2);
		System.out.println(student1);
	}
}
