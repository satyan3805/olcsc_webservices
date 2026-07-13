#!/bin/sh
tns=dbs01du.hctra.net:1521:htd1
usernamepwd=tag_owner/tag_ownerhtd1

export CLASSPATH="$ORACLE_HOME\jdbc\lib\classes12.jar;$ORACLE_HOME\sqlj\lib\runtime12.jar;$ORACLE_HOME\sqlj\lib\translator.jar;$ORACLE_HOME\sqlj\lib\runtime12ee.jar;.;$CLASSPATH"

echo "START JPublisher Script @ "`date`

echo "classpath -> $CLASSPATH"

export jar_version=1.1
export build_dir=target
export jpub_dir=$build_dir/jpub
export package=com.etcc.csc.plsql
export class_base=com
export out_loc=$class_base/etcc/csc/plsql
export jar_name=plsql
export source=sources
export class=classes
export class_full_dir=$class/$out_loc

CMD="rm -Rf $build_dir"
echo "$CMD"
$CMD

CMD="$ORACLE_HOME/bin/jpub.exe -input=jplsql/config.text -user=$usernamepwd -case=upper -methods=true -builtintypes=jdbc -numbertypes=jdbc -usertypes=oracle -dir=$jpub_dir -package=$package -url=jdbc:oracle:thin:@$tns"
echo "$CMD"
$CMD

mkdir $build_dir/$source
mkdir -p $build_dir/$class_full_dir
mv $jpub_dir/$out_loc/*.class $build_dir/$class_full_dir
mv $jpub_dir/* $build_dir/$source

cd $build_dir/$class
CMD="jar cvf ../${jar_name}-${jar_version}.jar *"
echo "$CMD"
$CMD

cd -
cd $build_dir/$source
CMD="jar cvf ../${jar_name}-${jar_version}-${source}.jar *"
echo "$CMD"
$CMD

echo "DONE @ "`date`
