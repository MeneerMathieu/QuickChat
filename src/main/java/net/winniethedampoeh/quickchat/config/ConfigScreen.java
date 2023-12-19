package net.winniethedampoeh.quickchat.config;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.MultiElementListEntry;
import me.shedaniel.clothconfig2.gui.entries.NestedListListEntry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;
import java.util.Optional;

public class ConfigScreen {
    public static ConfigBuilder makeConfigScreen(Screen parent){
        class Pair<T, R> {
            final T t;
            final R r;

            public Pair(T t, R r) {
                this.t = t;
                this.r = r;
            }

            public T getLeft() {
                return t;
            }

            public R getRight() {
                return r;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Pair<?, ?> pair = (Pair<?, ?>) o;

                if (!Objects.equals(t, pair.t)) return false;
                return Objects.equals(r, pair.r);
            }

            @Override
            public int hashCode() {
                int result = t != null ? t.hashCode() : 0;
                result = 31 * result + (r != null ? r.hashCode() : 0);
                return result;
            }
        }

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("title.quickchat.config"))
                .setDefaultBackgroundTexture(Identifier.of("minecraft", "textures/block/oak_planks.png"));
        builder.setGlobalized(true);
        builder.setGlobalizedExpanded(false);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.quickchat.general"));

        // QuickChat list
        general.addEntry(new NestedListListEntry<Pair<String, String>, MultiElementListEntry<Pair<String, String>>>(
                Text.translatable("entry.quickchat.quickchats"),
                Lists.newArrayList(new Pair<String, String>(Text.translatable("example.quickchat.quickchat").getString(), Text.translatable("example.quickchat.message").getString()), new Pair<>(Text.translatable("example.quickchat.quickchat").getString(), Text.translatable("example.quickchat.message").getString())), // Initial value
                true,
                Optional::empty,
                list -> {},
                () -> Lists.newArrayList(new Pair<>("test3", "test3"), new Pair<>("test4", "test4")), // Default value
                entryBuilder.getResetButtonKey(),
                true,
                true,
                (elem, nestedListListEntry) -> {
                    if (elem == null) {
                        Pair<String, String> newDefaultElemValue = new Pair<>(Text.translatable("example.quickchat.quickchat").getString(), Text.translatable("example.quickchat.message").getString());
                        return new MultiElementListEntry<>(Text.translatable("entry.quickchat.quickchat"), newDefaultElemValue,
                                Lists.newArrayList(entryBuilder.startStrField(Text.translatable("entry.quickchat.quickchat"), newDefaultElemValue.getLeft()).setDefaultValue(Text.translatable("example.quickchat.quickchat").getString()).build(),
                                        entryBuilder.startStrField(Text.translatable("entry.quickchat.message"), newDefaultElemValue.getRight()).setDefaultValue(Text.translatable("example.quickchat.message").getString()).build()),
                                true);
                    } else {
                        return new MultiElementListEntry<>(Text.translatable("entry.quickchat.quickchat"), elem,
                                Lists.newArrayList(entryBuilder.startStrField(Text.translatable("entry.quickchat.quickchat"), elem.getLeft()).setDefaultValue(Text.translatable("example.quickchat.quickchat").getString()).build(),
                                        entryBuilder.startStrField(Text.translatable("entry.quickchat.message"), elem.getRight()).setDefaultValue(Text.translatable("example.quickchat.message").getString()).build()),
                                true);
                    }
                }
        ));




        return builder;
    }
}
