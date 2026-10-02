import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CourseRegistrationTest {

    private CourseRegistration courseRegistration;

    @BeforeEach
    public void setUp() {
        courseRegistration = new CourseRegistration();
    }

    // ==================== Successful Registration Tests ====================

    @Test
    public void testRegisterCourse_Success_WhenAllConditionsMet() {
        boolean result = courseRegistration.registerCourse(true, false, false);
        assertTrue(result, "Should return true when user is logged in, course is not full, and not already registered");
    }

    // ==================== Not Logged In Tests ====================

    @Test
    public void testRegisterCourse_Failure_WhenNotLoggedIn() {
        boolean result = courseRegistration.registerCourse(false, false, false);
        assertFalse(result, "Should return false when user is not logged in");
    }

    @Test
    public void testRegisterCourse_Failure_WhenNotLoggedInAndCourseFull() {
        boolean result = courseRegistration.registerCourse(false, true, false);
        assertFalse(result, "Should return false when user is not logged in (regardless of course capacity)");
    }

    @Test
    public void testRegisterCourse_Failure_WhenNotLoggedInAndAlreadyRegistered() {
        boolean result = courseRegistration.registerCourse(false, false, true);
        assertFalse(result, "Should return false when user is not logged in (regardless of registration status)");
    }

    @Test
    public void testRegisterCourse_Failure_WhenNotLoggedInAndAllConditionsFail() {
        boolean result = courseRegistration.registerCourse(false, true, true);
        assertFalse(result, "Should return false when user is not logged in (even with other failures)");
    }

    // ==================== Course Full Tests ====================

    @Test
    public void testRegisterCourse_Failure_WhenCourseFull() {
        boolean result = courseRegistration.registerCourse(true, true, false);
        assertFalse(result, "Should return false when course is full");
    }

    @Test
    public void testRegisterCourse_Failure_WhenCourseFullAndAlreadyRegistered() {
        boolean result = courseRegistration.registerCourse(true, true, true);
        assertFalse(result, "Should return false when course is full (regardless of registration status)");
    }

    // ==================== Already Registered Tests ====================

    @Test
    public void testRegisterCourse_Failure_WhenAlreadyRegistered() {
        boolean result = courseRegistration.registerCourse(true, false, true);
        assertFalse(result, "Should return false when user is already registered");
    }

    // ==================== Parameterized Tests ====================

    @ParameterizedTest
    @CsvSource({
            "false, false, false, false",  // Not logged in
            "true, true, false, false",    // Course full
            "true, false, true, false",    // Already registered
            "false, true, false, false",   // Not logged in + Course full
            "false, false, true, false",   // Not logged in + Already registered
            "true, true, true, false",     // All conditions fail
            "true, false, false, true"     // All conditions pass
    })
    public void testRegisterCourse_AllScenarios(boolean loggedIn, boolean courseFull, 
                                                boolean alreadyRegistered, boolean expected) {
        boolean result = courseRegistration.registerCourse(loggedIn, courseFull, alreadyRegistered);
        assertEquals(expected, result, 
                String.format("Failed for loggedIn=%s, courseFull=%s, alreadyRegistered=%s", 
                        loggedIn, courseFull, alreadyRegistered));
    }

    // ==================== Edge Cases and Boundary Tests ====================

    @Test
    public void testRegisterCourse_AllParametersTrue() {
        boolean result = courseRegistration.registerCourse(true, true, true);
        assertFalse(result, "Should return false when all restrictive conditions are true");
    }

    @Test
    public void testRegisterCourse_AllParametersFalse_ExceptLoggedIn() {
        boolean result = courseRegistration.registerCourse(true, false, false);
        assertTrue(result, "Should return true when only loggedIn is true");
    }

    @Test
    public void testRegisterCourse_MultipleCallsWithSameParameters() {
        boolean result1 = courseRegistration.registerCourse(true, false, false);
        boolean result2 = courseRegistration.registerCourse(true, false, false);
        assertTrue(result1, "First call should succeed");
        assertTrue(result2, "Second call should also succeed (method should be idempotent regarding return value)");
    }
}
