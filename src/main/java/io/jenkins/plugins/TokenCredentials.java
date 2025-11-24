package io.jenkins.plugins;

import com.cloudbees.plugins.credentials.CredentialsDescriptor;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.impl.BaseStandardCredentials;
import hudson.Extension;
import hudson.util.FormValidation;
import hudson.util.Secret;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

public class TokenCredentials extends BaseStandardCredentials {

    private final Secret token;
    private final String tokenId;
    private final String tokenDescription;

    @DataBoundConstructor
    public TokenCredentials(
            CredentialsScope scope,
            String tokenId,
            String tokenDescription,
            Secret token
    ) {
        super(scope, tokenId, tokenDescription);
        this.token = token;
        this.tokenId = tokenId;
        this.tokenDescription = tokenDescription;
    }

    public Secret getToken() {
        return token;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getTokenDescription() {
        return tokenDescription;
    }

    // Descriptor for Jenkins UI
    @Symbol("vigilnzToken")
    @Extension
    public static class DescriptorImpl extends CredentialsDescriptor {

        @Override
        public String getDisplayName() {
            return "Vigilnz Security Token";
        }

        public FormValidation doCheckToken(@QueryParameter Secret token) {
            if (token == null || Secret.toString(token).isEmpty()) {
                return FormValidation.error("Field is required");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckTokenId(@QueryParameter String tokenId) {
            if (tokenId != null && !tokenId.trim().isEmpty()) {
                // Check for spaces
                if (tokenId.contains(" ")) {
                    return FormValidation.error("ID must not contain spaces.");
                }
                // Optional: only allow letters, numbers, dash and underscore
                if (!tokenId.matches("^[a-zA-Z0-9_-]+$")) {
                    return FormValidation.error("ID can only contain letters, numbers, - and _");
                }
            }
            return FormValidation.ok();
        }
    }
}
