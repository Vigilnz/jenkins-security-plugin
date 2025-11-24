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
//        getContext().get(TaskListener.class)
//                .getLogger()
//                .println("Option1: " + step.getToken() + ", Option2: " + step.getTargetFile());
//        // Add your custom logic here
//        getContext().onSuccess(null);
//        return true;
        TaskListener listener = getContext().get(TaskListener.class);
        Run<?, ?> run = getContext().get(Run.class);
        TokenCredentials creds =
                CredentialsProvider.findCredentialById(
                        step.getToken(),
                        TokenCredentials.class,
                        run
                );
        listener.getLogger().println("Credentials : " + creds);

        if (creds != null) {
            listener.getLogger().println("Token ID: " + creds.getTokenId());
            listener.getLogger().println("Description: " + creds.getTokenDescription());
            listener.getLogger().println("Token Value: " + creds.getToken().getPlainText());
        } else {
            listener.getLogger().println("No Vigilnz Token credential found");
        }

        getContext().onSuccess(null);
        return true;
    }
}