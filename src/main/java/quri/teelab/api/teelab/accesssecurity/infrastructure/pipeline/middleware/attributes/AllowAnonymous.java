package quri.teelab.api.teelab.accesssecurity.infrastructure.pipeline.middleware.attributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This attribute is used to decorate controllers and actions that do not require authorization.
 * It skips authorization if the action is decorated with @AllowAnonymous annotation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowAnonymous {
}