configureAndroidLib(project)

dependencies {

    api project(":lib.datastore")
    api project(":lib.setofatleastone")
    api project(":lib.domainobjects")

    // app compat
    api deps.support.core_utils
    api deps.support.app_compat
    api deps.support.recyclerview
}
