package io.jenkins.plugins;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Item;
import hudson.security.ACL;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import hudson.util.Secret;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;
import java.util.Collections;

// This file for Jenkins FreeStyle Job Method
public class SecurityCheckBuilder extends Builder {

    private final Secret token;
    private final String targetFile;

    @DataBoundConstructor
    public SecurityCheckBuilder(Secret token, String targetFile) {
        this.token = token;
        this.targetFile = targetFile;
    }

    public Secret getToken() {
        return token;
    }

    public String getTargetFile() {
        return targetFile;
    }

    // this function trigger when user click the build button
    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {

        listener.getLogger().println("------ Freestyle Method ------");

        String tokenText = token.getPlainText(); // actual value
        listener.getLogger().println("Your Token from Plugin: " + tokenText);
        listener.getLogger().println("Your Target File : " + targetFile);
        boolean result = ApiService.triggerScan(tokenText, targetFile, listener);

        if (!result) {
            listener.error("Scan failed");
            return false;
        }

        return true;
    }

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public String getDisplayName() {
            return "Invoke Vigilnz Security Task";   // This appears in dropdown
        }

        public ListBoxModel doFillTokenItems(@AncestorInPath Item project) {
            ListBoxModel items = new ListBoxModel();

            for (TokenCredentials c : CredentialsProvider.lookupCredentials(TokenCredentials.class, project, ACL.SYSTEM, Collections.emptyList())) {
                String label = c.getTokenDescription();
                if (label == null || label.isEmpty()) {
                    label = c.getId();
                }
                items.add(label, c.getId());
            }
            return items;
        }


        @Override
        public boolean isApplicable(Class jobType) {
            return true;
        }

        public FormValidation doCheckToken(@QueryParameter Secret token) {
            if (token == null || Secret.toString(token).isEmpty()) {
                return FormValidation.error("Token is required");
            }
            return FormValidation.ok();
        }
    }

}
