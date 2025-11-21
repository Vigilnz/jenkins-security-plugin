package io.jenkins.plugins;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import hudson.util.Secret;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.ForwardToView;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;

import java.io.IOException;

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

    public HttpResponse doAddToken() {
        return new ForwardToView(this, "addToken.jelly");
    }


    // this function trigger when user click the build button
    @Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener)
            throws InterruptedException, IOException {
        String tokenText = token.getPlainText(); // actual value
        listener.getLogger().println("Your Token from Plugin: " + tokenText);
        listener.getLogger().println("Your Target File : " + targetFile);
        return true;
    }

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public String getDisplayName() {
            return "Invoke Vigilnz Security Task";   // 👈 This appears in dropdown
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
