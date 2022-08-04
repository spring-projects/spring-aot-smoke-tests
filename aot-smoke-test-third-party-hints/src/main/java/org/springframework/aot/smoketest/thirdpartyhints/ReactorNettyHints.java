package org.springframework.aot.smoketest.thirdpartyhints;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;
import org.springframework.util.ClassUtils;

// TODO: Contribute those hints to reactor-netty
public class ReactorNettyHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		if (ClassUtils.isPresent("reactor.netty.ReactorNetty", classLoader)) {
			registerCoreHints(hints);
		}
		if (ClassUtils.isPresent("reactor.netty.http.HttpProtocol", classLoader)) {
			registerHttpHints(hints);
		}
	}

	private void registerCoreHints(RuntimeHints hints) {
		hints.reflection().registerType(TypeReference.of("reactor.netty.channel.ChannelOperationsHandler"),
				hint -> hint.withMembers(MemberCategory.INVOKE_PUBLIC_METHODS));
		hints.reflection().registerType(TypeReference.of("reactor.netty.transport.ServerTransport$Acceptor"),
				hint -> hint.withMembers(MemberCategory.INVOKE_PUBLIC_METHODS));
	}

	private void registerHttpHints(RuntimeHints hints) {
		hints.reflection().registerType(TypeReference.of("reactor.netty.http.server.HttpTrafficHandler"),
				hint -> hint.withMembers(MemberCategory.INVOKE_PUBLIC_METHODS));
	}

}
