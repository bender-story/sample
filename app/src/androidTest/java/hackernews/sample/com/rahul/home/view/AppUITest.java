package hackernews.sample.com.rahul.home.view;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import hackernews.sample.com.rahul.R;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AppUITest {



    @Rule
    public ActivityTestRule<HomeScreenActivity> mActivityTestRule = new ActivityTestRule<>(HomeScreenActivity.class);

    @Test
    public void homeScreenActivityTest() {

        ViewInteraction textView = onView(
                allOf(withText("List of Stories"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("List of Stories")));

            makeUIWait();



//        ViewInteraction recyclerView = onView(
//                allOf(withId(R.id.top_stories_recyclerview),
//                        childAtPosition(
//                                allOf(withId(R.id.swipe_container),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
//                                                0)),
//                                1),
//                        isDisplayed()));
//        recyclerView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.story_count), withText(" 3."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.top_stories_recyclerview),
                                        3),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText(" 3.")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.story_title), withText("Facebook’s teen app used a psychological trick to attract high school downloads"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Facebook’s teen app used a psychological trick to attract high school downloads")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.story_points), withText("73 time:1533685138"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("73 time:1533685138")));
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.story_comments), withText("|27 comments"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("|27 comments")));

        makeUIWait();

        onView(withId(R.id.top_stories_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        makeUIWait();

        secondScreen();

    }

    private void secondScreen(){

        ViewInteraction textView = onView(
                allOf(withId(R.id.story_header_title), withText("Facebook’s Teen App Used a Psychological Trick to Attract High School Downloads"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Facebook’s Teen App Used a Psychological Trick to Attract High School Downloads")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView), withText("Comments "),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.story_recyclerview),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Comments ")));

        onView(withId(R.id.story_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, scrollTo()));
        makeUIWait();

        // Get total item of myRecyclerView
        onView(withId(R.id.story_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, scrollTo()));
        int itemCount = mActivityTestRule.getActivity().recyclerView.getAdapter().getItemCount();



// Scroll to end of page with position
        onView(withId(R.id.story_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(itemCount - 1));

        makeUIWait();
        makeUIWait();

        Espresso.pressBack();

        makeUIWait();

    }
// make UI Wait for fe milli seconds
    private void makeUIWait(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
