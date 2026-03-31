package space.uselessidea.uibackend.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

@AnalyzeClasses(
    packages = "space.uselessidea.uibackend",
    importOptions = {ImportOption.DoNotIncludeTests.class})
class HexagonalArchitectureTest {

  @ArchTest
  static final ArchRule domain_should_not_depend_on_api_or_infrastructure =
      noClasses()
          .that()
          .resideInAPackage("..domain..")
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(
              "space.uselessidea.uibackend.api..", "space.uselessidea.uibackend.infrastructure..");

  @ArchTest
  static final ArchRule adapters_should_implement_domain_ports =
      classes()
          .that()
          .resideInAPackage("..infrastructure..")
          .and()
          .haveSimpleNameEndingWith("Adapter")
          .should(implementAnyDomainPortInterface());

  @ArchTest
  static final ArchRule controllers_should_not_depend_on_repositories =
      noClasses()
          .that()
          .resideInAPackage("..api..controller..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("..repository..");

  private static ArchCondition<JavaClass> implementAnyDomainPortInterface() {
    return new ArchCondition<>("implement at least one domain port interface") {
      @Override
      public void check(JavaClass item, ConditionEvents events) {
        boolean implementsDomainPort =
            item.getAllRawInterfaces().stream()
                .anyMatch(
                    iface ->
                        iface.getPackageName().contains(".domain.")
                            && iface.getSimpleName().endsWith("Port"));
        String message =
            item.getName() + " implements domain port interface: " + implementsDomainPort;
        events.add(new SimpleConditionEvent(item, implementsDomainPort, message));
      }
    };
  }
}
