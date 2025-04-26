package main.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;


public class StudentList implements Iterable<Student> {
	// Technically a course has a student list, which has students innit 
	// kinda like the same idea of Card/CardStack/Game, but in this case 
	// we have 					Student/StudentList/Course  :)
	
	private ArrayList<Student> internalStudentList;
	
	
	public StudentList() {
		this.internalStudentList = new ArrayList<Student>();
	}

	public StudentList(StudentList other) {
		this.internalStudentList = new ArrayList<Student>(other.internalStudentList);
	}
	
	public void add(Student student) {
		internalStudentList.add(student); 
	}

	
	public void remove(Student student) {
		internalStudentList.remove(student);
	}

	public Student get(int index) {
		return internalStudentList.get(index);
	}
	
    public void sortByFirstName() {
        Collections.sort(internalStudentList, Student.sortByFirstName());
    }
    
    public void sortByLastName() {
        Collections.sort(internalStudentList, Student.sortByLastName());
    }

    public void sortByUsername() {
        Collections.sort(internalStudentList, Student.sortByUsername());
    }

    public void sortByAssignmentGrade(String assignmentName) {
    	Collections.sort(internalStudentList, Student.sortByGradeOnAssignment(assignmentName));
    }
    
	@Override
	public Iterator<Student> iterator() {
		return this.internalStudentList.iterator();
	}
	
	public boolean isEmpty() {
		return this.internalStudentList.isEmpty();
	}

	public int size() {
		return this.internalStudentList.size();
	}

	public void merge(StudentList other) {
		this.internalStudentList.addAll(other.internalStudentList);
		HashSet<Student> uniqueStudents = new HashSet<>();
		uniqueStudents.addAll(this.internalStudentList);
		this.internalStudentList.clear();
		this.internalStudentList.addAll(uniqueStudents);
	}
	
	public double getClassMedian(Course course) {
        ArrayList<Double> percentages = new ArrayList<>();

        for (Student student : this) {
            StudentGradebook gb = student.getGradebookForCourse(course);
            if (gb == null || gb.getAssignments().isEmpty()) continue;

            double earned = 0;
            double possible = 0;

            for (Assignment a : gb.getAssignments()) {
                earned += a.getPointsEarned();
                possible += a.getMaxPoints();
            }

            if (possible > 0) {
                percentages.add((earned / possible) * 100);
            }
        }

        if (percentages.isEmpty()) return 0.0;

        Collections.sort(percentages);
        int n = percentages.size();
        if (n % 2 == 1) {
            return percentages.get(n / 2);
        } else {
            return (percentages.get(n / 2 - 1) + percentages.get(n / 2)) / 2.0;
        }
    }

    
    public double getClassAverage(Course course) {
        double totalEarned = 0;
        double totalPossible = 0;

        for (Student student : this) {
            StudentGradebook gb = student.getGradebookForCourse(course);
            if (gb == null) continue;

            for (Assignment a : gb.getAssignments()) {
                totalEarned += a.getPointsEarned();
                totalPossible += a.getMaxPoints();
            }
        }

        return totalPossible > 0 ? (totalEarned / totalPossible) * 100 : 0.0;
    }
    
 // in StudentList.java
    public List<StudentList> partition(int maxPerGroup) {
        if (maxPerGroup <= 0) throw new IllegalArgumentException("group size must be positive");
        List<StudentList> groups = new ArrayList<>();
        for (int i = 0; i < internalStudentList.size(); i += maxPerGroup) {
            StudentList chunk = new StudentList();
            int end = Math.min(i + maxPerGroup, internalStudentList.size());
            for (int j = i; j < end; j++) {
                chunk.add(internalStudentList.get(j));
            }
            groups.add(chunk);
        }
        return groups;
    }


}
