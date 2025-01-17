package io.github.flemmli97.improvedmobs.config;

import io.github.flemmli97.tenshilib.api.config.IConfigListValue;
import io.github.flemmli97.tenshilib.common.utils.SearchUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class DifficultyConfig implements IConfigListValue<DifficultyConfig> {

    private static final Pair<Float, Float> defaultVal = Pair.of(0f, 0.1f);
    private final List<Pair<Float, Float>> vals = new ArrayList<>();

    @Override
    public DifficultyConfig readFromString(List<String> ss) {
        this.vals.clear();
        List<Pair<Float, Float>> list = new ArrayList<>();
        for (String s : ss) {
            String[] parts = s.split("-");
            if (parts.length != 2)
                continue;
            this.vals.add(Pair.of(Float.parseFloat(parts[0]), Float.parseFloat(parts[1])));
        }
        list.sort((o1, o2) -> Float.compare(o1.getLeft(), o2.getLeft()));
        this.vals.addAll(list);
        return this;
    }

    @Override
    public List<String> writeToString() {
        List<String> list = new ArrayList<>();
        this.vals.forEach(v -> list.add(v.getLeft() + "-" + v.getRight()));
        return list;
    }

    public float get(float difficulty) {
        return SearchUtils.searchInfFunc(this.vals, v -> Float.compare(v.getLeft(), difficulty), defaultVal).getRight();
    }
}