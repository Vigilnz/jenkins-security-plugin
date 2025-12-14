package io.jenkins.plugins.vigilnz.pipeline;

import hudson.Extension;
import hudson.model.TaskListener;
import jakarta.annotation.Nonnull;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.List;
import java.util.Set;

/**
 * Pipeline step for Vigilnz security scans.
 * Security: The 'credentialsId' field stores only a credential ID (identifier), not the actual token value.
 * The actual token is stored securely in TokenCredentials using Secret.
 */
public class PipelineStep extends Step {

    /** 
     * Credential ID (not sensitive - just an identifier to look up the actual credential).
     * The actual token is stored securely in TokenCredentials using Secret.
     */
    private final String credentialsId;
    private final List<String> scanTypes;
    private String targetFile;  // Optional parameter

    @DataBoundConstructor
    public PipelineStep(String credentialsId, List<String> scanTypes) {
        this.credentialsId = credentialsId;
        this.scanTypes = scanTypes != null ? scanTypes : List.of();
    }

    @DataBoundSetter
    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public String getCredentialsId() {
        return credentialsId;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public List<String> getScanTypes() {
        return scanTypes != null ? scanTypes : List.of();
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new PipelineStepExecution(this, context);
    }

    @Extension
    public static class DescriptorImpl extends StepDescriptor {

        @Override
        public String getFunctionName() {
            return "vigilnzScan";   // This is the pipeline function name
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Run Vigilnz Security Scan";
        }

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return Set.of(TaskListener.class);
        }
    }

}
