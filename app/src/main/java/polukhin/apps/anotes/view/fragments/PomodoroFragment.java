package polukhin.apps.anotes.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import polukhin.apps.anotes.R;

public class PomodoroFragment extends Fragment {

    private Button mButtonStartTimer;
    private Button mButtonStopTimer;
    private Button mButtonPauseTimer;
    private TextInputLayout mEditTextInput;
    private TextView mTextViewOutput;
    private CountDownTimer countDownTimer;
    private long residueTime;
    private boolean buttonPauseIsPressed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pomodoro, container, false);

        ImageView imageViewBack = root.findViewById(R.id.backFromPomodoro);
        mEditTextInput = root.findViewById(R.id.editTextTimer);
        mButtonStartTimer = root.findViewById(R.id.buttonStartTimer);
        mButtonStopTimer = root.findViewById(R.id.buttonStopTimer);
        mButtonPauseTimer = root.findViewById(R.id.buttonPauseTimer);
        mTextViewOutput = root.findViewById(R.id.outputTextPomodoro);

        buttonPauseIsPressed = false;

        mButtonStartTimer.setOnClickListener(v ->
                launchTimer(mEditTextInput.getEditText().getText().toString(), "minutes"));

        mButtonStopTimer.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                residueTime = 0;
                mButtonStartTimer.setEnabled(true);
                mTextViewOutput.setText("");
                countDownTimer = null;
                buttonPauseIsPressed = false;
                mButtonPauseTimer.setText("Пауза");
            }
        });

        mButtonPauseTimer.setOnClickListener(v -> {
            if (countDownTimer != null) {
                mButtonStartTimer.setEnabled(true);
                countDownTimer.cancel();
                if (!buttonPauseIsPressed) {
                    mButtonPauseTimer.setText("Продолжить");
                    buttonPauseIsPressed = true;
                    mButtonStartTimer.setEnabled(false);
                } else {
                    mButtonPauseTimer.setText("Пауза");
                    buttonPauseIsPressed = false;
                    launchTimer(String.valueOf((int) residueTime / 1000), "seconds");
                }
            }
        });

        imageViewBack.setOnClickListener(v ->
                Navigation.findNavController(root).navigate(R.id.action_from_pomodoro));
        return root;
    }

    public void launchTimer(String text, String time) {
        if (text.length() > 0 && !text.equals("0")) {
            if (time.equals("minutes")) {
                text = Integer.parseInt(text) > 60 ? "3600"
                        : String.valueOf(Integer.parseInt(text) * 60);
            } else {
                text = Integer.parseInt(text) > 3600 ? "3600" : text;
            }
            mButtonStartTimer.setEnabled(false);
            int seconds = Integer.parseInt(text);
            countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    residueTime = millisUntilFinished;
                    int minutes = (int) millisUntilFinished / 60000;
                    int seconds = (int) millisUntilFinished % 60000 / 1000;

                    String minutesStr = String.valueOf(minutes);
                    String secondsStr = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
                    mTextViewOutput.setText(String.format("%s:%s", minutesStr, secondsStr));
                }

                @Override
                public void onFinish() {
                    mButtonStartTimer.setEnabled(true);
                    mTextViewOutput.setText("Финиш");
                    countDownTimer = null;
                }
            }.start();
        }
    }
}