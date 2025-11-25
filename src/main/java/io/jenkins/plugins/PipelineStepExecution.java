package io.jenkins.plugins;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import hudson.model.Run;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;

public class PipelineStepExecution extends StepExecution {
    private final transient PipelineStep step;

    public PipelineStepExecution(PipelineStep step, StepContext context) {
        super(context);
        this.step = step;
    }

    @Override
    public boolean start() throws Exception {

        TaskListener listener = getContext().get(TaskListener.class);
        Run<?, ?> run = getContext().get(Run.class);
        TokenCredentials creds =
                CredentialsProvider.findCredentialById(
                        step.getToken(),
                        TokenCredentials.class,
                        run
                );
        listener.getLogger().println("------ Pipeline Method ------");

        if (creds != null) {
            listener.getLogger().println("Token ID: " + creds.getTokenId());
            listener.getLogger().println("Description: " + creds.getTokenDescription());

            String token = creds.getToken().getPlainText();
            boolean result = ApiService.triggerScan(token, step.getTargetFile(), listener);

            if (!result) {
                listener.error("Scan failed");
                return false;
            }
        } else {
            listener.getLogger().println("No Vigilnz Token credential found");
        }

        getContext().onSuccess(null);
        return true;
    }
}