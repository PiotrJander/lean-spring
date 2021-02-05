/**
 * The `impl` package contains implementations of endpoints.
 *
 * The intention is that each endpoint should correspond to one sub-package of `impl`.
 * Endpoint implementations can depend on `common` and `database` packages, but they
 * should not depend on each other. Reusable code should live in the `common` package.
 *
 * An exception to the "one endpoint, one package" rule is when a group of endpoints implement
 * CRUD-esqe operations on a resource. CRUD operations on a single resource should belong
 * in the same package, named after the resource.
 */
package ai.neptune.usermanager.impl;
