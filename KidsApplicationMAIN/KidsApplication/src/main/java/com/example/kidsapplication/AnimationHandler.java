package com.example.kidsapplication;


import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AnimationHandler {
    private static final Logger LOGGER = Logger.getLogger(AnimationHandler.class.getName());
    private final ImageView avatarImageView;
    private final Map<String, Image[]> animationFrames;
    private final Map<String, Timeline> animations;
    private String currentAnimation;

    public AnimationHandler(ImageView avatarImageView) {
        this.avatarImageView = avatarImageView;
        this.animationFrames = new HashMap<>();
        this.animations = new HashMap<>();
        this.currentAnimation = "idle";
        initializeAnimations();
    }

    private void initializeAnimations() {
        try {
            // Load animation frames for different states
            loadAnimationFrames("idle", "/animations/idle/", 4);
            loadAnimationFrames("talking", "/animations/talking/", 4);
            loadAnimationFrames("thinking", "/animations/thinking/", 4);
            loadAnimationFrames("happy", "/animations/happy/", 4);
            loadAnimationFrames("sad", "/animations/sad/", 4);

            // Create animations
            createAnimation("idle", Duration.millis(500));
            createAnimation("talking", Duration.millis(300));
            createAnimation("thinking", Duration.millis(400));
            createAnimation("happy", Duration.millis(400));
            createAnimation("sad", Duration.millis(400));

            // Start idle animation by default
            playAnimation("idle");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize animations", e);
        }
    }

    private void loadAnimationFrames(String state, String path, int frameCount) {
        Image[] frames = new Image[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String framePath = path + "frame" + i + ".png";
            try {
                frames[i] = new Image(getClass().getResourceAsStream(framePath));
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to load animation frame: " + framePath, e);
            }
        }
        animationFrames.put(state, frames);
    }

    private void createAnimation(String state, Duration frameDuration) {
        Image[] frames = animationFrames.get(state);
        if (frames == null || frames.length == 0) {
            LOGGER.warning("No frames found for animation state: " + state);
            return;
        }

        Timeline timeline = new Timeline();
        for (int i = 0; i < frames.length; i++) {
            final int frameIndex = i;
            KeyFrame keyFrame = new KeyFrame(
                    frameDuration.multiply(i),
                    event -> avatarImageView.setImage(frames[frameIndex])
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        // Add final keyframe to loop the animation
        KeyFrame finalKeyFrame = new KeyFrame(
                frameDuration.multiply(frames.length),
                event -> timeline.playFromStart()
        );
        timeline.getKeyFrames().add(finalKeyFrame);

        animations.put(state, timeline);
    }

    public void playAnimation(String state) {
        if (state.equals(currentAnimation)) {
            return;
        }

        // Stop current animation
        Timeline currentTimeline = animations.get(currentAnimation);
        if (currentTimeline != null) {
            currentTimeline.stop();
        }

        // Start new animation
        Timeline newTimeline = animations.get(state);
        if (newTimeline != null) {
            newTimeline.play();
            currentAnimation = state;
        } else {
            LOGGER.warning("Animation not found for state: " + state);
        }
    }

    public void stopAllAnimations() {
        animations.values().forEach(Timeline::stop);
        currentAnimation = "idle";
    }

    public void dispose() {
        stopAllAnimations();
        animations.clear();
        animationFrames.clear();
    }
}
