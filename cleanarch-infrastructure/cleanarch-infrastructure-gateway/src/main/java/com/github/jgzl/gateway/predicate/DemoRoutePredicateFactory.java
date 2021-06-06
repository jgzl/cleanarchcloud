/*
 * Copyright (c) 2019-2019
 *//*


package com.github.jgzl.conversion.platform.gateway.predicate;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.putUriTemplateVariables;
import static org.springframework.http.server.PathContainer.parsePath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.core.style.ToStringCreator;
import org.springframework.http.server.PathContainer;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPattern.PathMatchInfo;
import org.springframework.web.util.pattern.PathPatternParser;

import lombok.extern.slf4j.Slf4j;

*/
/**
 * @author lihaifeng
 *//*

@Slf4j
public class DemoRoutePredicateFactory extends AbstractRoutePredicateFactory<DemoRoutePredicateFactory.Config> {

  public DemoRoutePredicateFactory() {
    super(Config.class);
  }

  private static final String MATCH_OPTIONAL_TRAILING_SEPARATOR_KEY = "matchOptionalTrailingSeparator";

  private PathPatternParser pathPatternParser = new PathPatternParser();

  private static void traceMatch(String prefix, Object desired, Object actual,
      boolean match) {
    if (log.isTraceEnabled()) {
      String message = String.format("%s \"%s\" %s against value \"%s\"", prefix,
          desired, match ? "matches" : "does not match", actual);
      log.trace(message);
    }
  }

  public void setPathPatternParser(PathPatternParser pathPatternParser) {
    this.pathPatternParser = pathPatternParser;
  }

  @Override
  public List<String> shortcutFieldOrder() {
    return Arrays.asList("patterns", MATCH_OPTIONAL_TRAILING_SEPARATOR_KEY);
  }

  @Override
  public ShortcutType shortcutType() {
    return ShortcutType.GATHER_LIST_TAIL_FLAG;
  }


  @Override
  public Predicate<ServerWebExchange> apply(Config config) {
    log.info("demo{}", config.getPatterns());
    final ArrayList<PathPattern> pathPatterns = new ArrayList<>();
    synchronized (this.pathPatternParser) {
      pathPatternParser.setMatchOptionalTrailingSeparator(
          config.isMatchOptionalTrailingSeparator());
      config.getPatterns().forEach(pattern -> {
        PathPattern pathPattern = this.pathPatternParser.parse(pattern);
        pathPatterns.add(pathPattern);
      });
    }
    return new GatewayPredicate() {
      @Override
      public boolean test(ServerWebExchange exchange) {
        PathContainer path = parsePath(
            exchange.getRequest().getURI().getRawPath());

        Optional<PathPattern> optionalPathPattern = pathPatterns.stream()
            .filter(pattern -> pattern.matches(path)).findFirst();

        if (optionalPathPattern.isPresent()) {
          PathPattern pathPattern = optionalPathPattern.get();
          traceMatch("Pattern", pathPattern.getPatternString(), path, true);
          PathMatchInfo pathMatchInfo = pathPattern.matchAndExtract(path);
          putUriTemplateVariables(exchange, pathMatchInfo.getUriVariables());
          return true;
        } else {
          traceMatch("Pattern", config.getPatterns(), path, false);
          return false;
        }
      }

      @Override
      public String toString() {
        return String.format("Paths: %s, match trailing slash: %b",
            config.getPatterns(), config.isMatchOptionalTrailingSeparator());
      }
    };
  }

  public static class Config {

    private List<String> patterns = new ArrayList<>();

    private boolean matchOptionalTrailingSeparator = true;

    @Deprecated
    public String getPattern() {
      if (!CollectionUtils.isEmpty(this.patterns)) {
        return patterns.get(0);
      }
      return null;
    }

    @Deprecated
    public DemoRoutePredicateFactory.Config setPattern(String pattern) {
      this.patterns = new ArrayList<>();
      this.patterns.add(pattern);
      return this;
    }

    public List<String> getPatterns() {
      return patterns;
    }

    public DemoRoutePredicateFactory.Config setPatterns(List<String> patterns) {
      this.patterns = patterns;
      return this;
    }

    public boolean isMatchOptionalTrailingSeparator() {
      return matchOptionalTrailingSeparator;
    }

    public DemoRoutePredicateFactory.Config setMatchOptionalTrailingSeparator(
        boolean matchOptionalTrailingSeparator) {
      this.matchOptionalTrailingSeparator = matchOptionalTrailingSeparator;
      return this;
    }

    @Override
    public String toString() {
      return new ToStringCreator(this).append("patterns", patterns)
          .append("matchOptionalTrailingSeparator",
              matchOptionalTrailingSeparator)
          .toString();
    }
  }
}
*/
