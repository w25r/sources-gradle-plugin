package com.github.skazzyy.gradle.plugins.sources;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.javadoc.Javadoc;

/**
 * Created by w25r on 3/27/14.
 */
public class SourcesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        //TODO change this to check if it's applied
        project.getPlugins().apply(JavaPlugin.class);
        createSourcesJars(project);
    }

    protected void createSourcesJars(final Project project) {

        final JavaPluginConvention javaPluginConvention = project.getConvention().getPlugin(JavaPluginConvention.class);
        Task sourcesJar = project.getTasks().create("sourcesJar", Jar.class, new Action<Jar>() {
            @Override
            public void execute(Jar jar) {
                jar.setDescription("Creates a jar with the sources");
                jar.setClassifier("sources");
                jar.from(javaPluginConvention.getSourceSets().getByName("main").getAllSource());
            }
        });
        Task javadocJar = project.getTasks().create("javadocJar", Jar.class, new Action<Jar>() {
            @Override
            public void execute(Jar jar) {
                jar.setDescription("Creates a javadoc jar");
                jar.setClassifier("javadoc");
                Javadoc javadoc = (Javadoc) project.getTasks().getByName(JavaPlugin.JAVADOC_TASK_NAME);
                jar.from(javadoc.getDestinationDir());
            }
        });
        Task testsJar = project.getTasks().create("testsJar", Jar.class, new Action<Jar>() {
            @Override
            public void execute(Jar jar) {
                jar.setDescription("Creates a jar containing all the source and classes for the tests");
                jar.setClassifier("tests");

                SourceSet test = javaPluginConvention.getSourceSets().getByName("test");
                jar.from(test.getAllJava());
                jar.from(test.getOutput());
            }
        });

        project.getArtifacts().add(Dependency.ARCHIVES_CONFIGURATION, sourcesJar);
        project.getArtifacts().add(Dependency.ARCHIVES_CONFIGURATION, javadocJar);
        project.getArtifacts().add(Dependency.ARCHIVES_CONFIGURATION, testsJar);
    }
}
