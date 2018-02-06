package org.eclipse.buildship.ui.view.task

import org.gradle.tooling.model.GradleProject
import org.gradle.tooling.model.eclipse.EclipseProject

import com.google.common.base.Optional

import org.eclipse.buildship.core.CorePlugin
import org.eclipse.buildship.core.configuration.BuildConfiguration
import org.eclipse.buildship.core.util.gradle.Path
import org.eclipse.buildship.ui.test.fixtures.WorkspaceSpecification

/**
 * Base class for tests related to Gradle Views.
 */
abstract class ViewSpecification extends WorkspaceSpecification {

  protected def newProjectNode(ProjectNode parent, String projectLocation) {
    return new ProjectNode(parent, newEclipseProject(parent, projectLocation), newGradleProject(), Optional.absent(), false, Mock(OmniBuildInvocations))
  }

  protected ProjectTaskNode newProjectTaskNode(ProjectNode parent, String taskPath) {
    ProjectTask projectTask = Stub(ProjectTask) {
        getPath() >> Path.from(taskPath)
    }
    new ProjectTaskNode(parent, projectTask)
  }

  protected TaskSelectorNode newTaskSelectorNode(ProjectNode parent) {
    TaskSelector taskSelector = Stub(TaskSelector)
    new TaskSelectorNode(parent, taskSelector)
  }

  private EclipseProject newEclipseProject(ProjectNode parentNode, String path) {
    File projectDir = dir(path)
    BuildConfiguration buildConfiguration = createInheritingBuildConfiguration(projectDir)
    CorePlugin.configurationManager().saveBuildConfiguration(buildConfiguration)
    EclipseProject eclipseProject = Stub(EclipseProject) {
        getProjectDirectory() >> projectDir
        getParent() >> parentNode?.eclipseProject
    }
  }

  private GradleProject newGradleProject() {
    Stub(GradleProject)
  }

}
