package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests Activity for checking if there are any schedule conflicts
 * 
 * @author Niyati Patel
 */
class ActivityTest {

	/**
	 * Tests if there are conflicts between two courses that do not conflict with
	 * each other
	 */
	@Test
	public void testCheckConflict() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 40, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 40, "TH", 1330, 1445);

		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
	}

	/**
	 * Tests if there are conflicts between two courses that conflict with each
	 * other
	 */
	@Test
	public void testCheckConflictWithConflict() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 40, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 40, "M", 1330, 1445);

		Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
		assertEquals("Schedule conflict.", e1.getMessage());

		Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
		assertEquals("Schedule conflict.", e2.getMessage());
	}

}
