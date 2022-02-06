package com.training.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.training.util.CurrentUserProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware <String> {

	private final CurrentUserProvider currentUserProvider;
	
    @Override
    public Optional <String> getCurrentAuditor() {
        String userId = currentUserProvider.getCurrentUser().getUserId();
        return Optional.of(userId);
    }
}