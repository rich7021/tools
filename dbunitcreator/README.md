# Description

It's a simple tool create dbunit xml from exported file from oracle(probably for other database).

# Usage

Prepare a txt with following format named raw.txt, and put it into resources folder.

```
Table_name
Column1\tColumn2\tColumn3
Value1-1\tValue2-1\tValue3-1
Value1-2\t[null]\tValue3-2
```

After execute it, there will bea result.txt in the resources folder.
```
<Table_name Column1="Value1-1" Column2="Value2-1" Column3="Value3-1" />
<Table_name Column1="Value1-2" Column2="[null]" Column3="Value3-2" />
```

just copy paste into the dbunit xml file.
