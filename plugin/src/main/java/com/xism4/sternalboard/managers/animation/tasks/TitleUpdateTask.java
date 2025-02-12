package com.xism4.sternalboard.managers.animation.tasks;

import com.xism4.sternalboard.SternalBoardPlugin;
import com.xism4.sternalboard.managers.ScoreboardManager;
import com.xism4.sternalboard.managers.animation.AnimationManager;
import com.xism4.sternalboard.utils.TextUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TitleUpdateTask extends BukkitRunnable {
    private final List<String> lines;
    private final AnimationManager animationManager;
    private final ScoreboardManager scoreboardManager;
    private final SternalBoardPlugin plugin;
    private int index;

    public TitleUpdateTask(SternalBoardPlugin plugin, AnimationManager animationManager, List<String> lines) {
        this.plugin = plugin;
        this.scoreboardManager = plugin.getScoreboardManager();
        this.animationManager = animationManager;
        this.lines = lines;
        this.index = 0;
    }

    @Override
    public void run() {
        animationManager.setTitle(lines.get(index));
        index = (index + 1) % lines.size();

        scoreboardManager.getBoardsHandler().values().forEach(sb -> {
            var title = TextUtils.processPlaceholders(plugin, sb.getPlayer(), animationManager.getTitle());
            sb.updateTitle(title);
        });
    }
}
