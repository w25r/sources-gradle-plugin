package com.github.skazzyy.gradle.plugins.sources;

import org.gradle.api.internal.project.DefaultProject;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

import java.io.File;

/**
 * Created by w25r on 3/27/14.
 */
public class SourcesPluginTest {

    @Test
    public void shouldApplyPlugin() {
        DefaultProject project = (DefaultProject) ProjectBuilder.builder().withProjectDir(new File("build/tmp/" + getClass().getSimpleName())).build();
        project.getPlugins().apply(SourcesPlugin.class);
    }
}
