package com.github.skazzyy.gradle.plugins.sources

import org.gradle.api.internal.project.DefaultProject
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

/**
 * Created by w25r on 3/27/14.
 */
class SmokeTest {

    @Test
    void shouldWork() {
        DefaultProject project = (DefaultProject) ProjectBuilder.builder().withProjectDir(new File("build/tmp/" + getClass().getSimpleName())).build();
        project.getPlugins().apply(SourcesPlugin.class);
        project.tasks.each {
            println it
        }
        project.tasks.getByName('sourcesJar').execute()
    }
}
