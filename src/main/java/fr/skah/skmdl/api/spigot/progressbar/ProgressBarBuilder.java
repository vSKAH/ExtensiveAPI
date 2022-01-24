package fr.skah.skmdl.api.spigot.progressbar;

/*
 *  * @Created on 2021 - 14:53
 *  * @Project UtilsAPI
 *  * @Author Jimmy
 */

public class ProgressBarBuilder {

    private ProgressBarOrientation progressBarOrientation;
    private ProgressBarType progressBarType;
    private int progressValue;
    private int progressMaxValue;

    private char patternColor;
    private char progressColor;

    public ProgressBarBuilder setBarOrientation(ProgressBarOrientation progressBarOrientation) {
        this.progressBarOrientation = progressBarOrientation;
        return this;
    }

    public ProgressBarBuilder setBarType(ProgressBarType progressBarType) {
        this.progressBarType = progressBarType;
        return this;
    }

    public ProgressBarBuilder setProgressBarMaxValue(int progressBarMaxValue) {
        this.progressMaxValue = progressBarMaxValue;
        return this;
    }

    public ProgressBarBuilder setProgressBarValue(int progressValue) {
        this.progressValue = progressValue;
        return this;
    }

    public ProgressBarBuilder setPaternColor(char character) {
        this.patternColor = character;
        return this;
    }

    public ProgressBarBuilder setProgressColor(char character) {
        this.progressColor = character;
        return this;
    }


    public String build(char characterUsed) {
        final StringBuilder stringBuilder = new StringBuilder();

        if(progressBarType == ProgressBarType.CLASSIC) {
            final int coloredCharacter = progressMaxValue * (progressValue * 100 / 60) / 100;
            if(progressBarOrientation == ProgressBarOrientation.RIGHT_LEFT) {
                for (int i = 0; i < coloredCharacter; i++) stringBuilder.append('§').append(progressColor).append(characterUsed);
                for (int i = 0; i < (progressMaxValue - coloredCharacter); i++) stringBuilder.append('§').append(patternColor).append(characterUsed);
            }
            if(progressBarOrientation == ProgressBarOrientation.LEFT_RIGHT) {
                for (int i = 0; i < (progressMaxValue - coloredCharacter); i++) stringBuilder.append('§').append(patternColor).append(characterUsed);
                for (int i = 0; i < coloredCharacter; i++) stringBuilder.append('§').append(progressColor).append(characterUsed);
            }
            return stringBuilder.toString();
        }


        if(progressBarType == ProgressBarType.AKIRA) {
            if(progressBarOrientation == ProgressBarOrientation.RIGHT_LEFT) {
                for (int i = progressMaxValue; i > 0; i--) {
                    if (i == progressValue) stringBuilder.append('§').append(progressColor).append(characterUsed);
                    else stringBuilder.append('§').append(patternColor).append(characterUsed);
                }
            }
            if(progressBarOrientation == ProgressBarOrientation.LEFT_RIGHT) {
                for (int i = 0; i < progressMaxValue; i++) {
                    if (i == progressValue) stringBuilder.append('§').append(progressColor).append(characterUsed);
                    else stringBuilder.append('§').append(patternColor).append(characterUsed);
                }
            }
            return stringBuilder.toString();
        }

        return "error";
    }

}
