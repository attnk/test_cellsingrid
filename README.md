# Connected Cells in a Grid
___
Projeto utilizado para escrever e testar localmente um algoritmo para resolver o exercício [Connected Cells in a Grid].

### Tecnologia
 - Java 8

### Solução
A classe que contém exatamente o código para ser utilizado como solução está dentro do pacote `solution` (para o caminho completo clique [aqui](https://github.com/attnk/test_cellsingrid/blob/feature/v1/testConnectedCellsInGrid/src/solution/Solution.java)).

**OBS:** A classe [Solution.java], é exatamente uma cópia da classe gerada pelo sistema do HackerRank, sendo que basta copiar a mesma para o sistema e executar o programa. 
Não esquecer de copiar as dependências de `import` dos pacotes utilizados para criação da solução:

```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
```

Sendo que os métodos `getLastTotal` e `matrixToArray`, são métodos (privados criados após refatoração) criados com a finalidade de diminnuir um pouco a complexidade da leitura e manutenção na solução criada.

[//]:#

[Connected Cells in a Grid]:<https://www.hackerrank.com/challenges/connected-cell-in-a-grid/problem>
[Solution.java]:<https://github.com/attnk/test_cellsingrid/blob/feature/v1/testConnectedCellsInGrid/src/solution/Solution.java>